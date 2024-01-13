package com.bank.MyBankApp.account.service;

import com.bank.MyBankApp.account.dto.request.*;
import com.bank.MyBankApp.account.dto.response.CreateAccountResponse;
import com.bank.MyBankApp.account.dto.response.TransactionResponse;
import com.bank.MyBankApp.account.model.Account;
import com.bank.MyBankApp.account.model.AccountType;
import com.bank.MyBankApp.account.repository.AccountRepository;
import com.bank.MyBankApp.appUser.model.AppUser;
import com.bank.MyBankApp.customer.model.Customer;
import com.bank.MyBankApp.customer.repoistory.CustomerRepository;
import com.bank.MyBankApp.exception.AlreadyExistsException;
import com.bank.MyBankApp.exception.InvalidCredentialException;
import com.bank.MyBankApp.exception.MyBankException;
import com.bank.MyBankApp.exception.NotFoundException;
import com.bank.MyBankApp.mail.MailService;
import com.bank.MyBankApp.transaction.model.Transaction;
import com.bank.MyBankApp.transaction.model.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.bank.MyBankApp.utilities.MyBankAppUtils.GET_DEPOSIT_MAIL_TEMPLATE;
import static com.bank.MyBankApp.utilities.MyBankAppUtils.GET_WITHDRAW_MAIL_TEMPLATE;
import static com.bank.MyBankApp.utilities.MyBankAppUtils.GET_TRANSFER_MAIL_TEMPLATE;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{
    private static final PasswordEncoder PIN_ENCODER =
            Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final MailService mailService;


    @Override
    public CreateAccountResponse createNewAccount(CreateAccountRequest request) {
        Customer customer = findCustomerById(request.getCustomerId());
        checkIfCustomerHasAccountType(request.getCustomerId(), request.getAccountType());
        Account account = new Account();
        String accountName = createAccountName(customer.getAppUser());
        account.setAccountName(accountName);
        account.setAccountPin(hashPin(request.getPin()));
        account.setIban(generateRandomIban());
        account.setAccountNumber(generateAccountNumber());
        account.setAccountType(request.getAccountType());
        account.setCustomer(customer);
        accountRepository.save(account);
        return getAccountResponse(account);
    }

    private void checkIfCustomerHasAccountType(Integer customerId, AccountType accountType) {
        if(accountRepository.existsByAccountTypeAndCustomerId(accountType, customerId))
                throw new AlreadyExistsException("Customer is not allowed to create an account with the same account type.");
    }

    private String createAccountName(AppUser appUser) {
        return "%s %s".formatted(appUser.getFirstName(), appUser.getLastName());
    }

    private static String hashPin(String pin) {
        return PIN_ENCODER.encode(pin);
    }


    private String generateRandomIban() {
        String iban = Iban.random(CountryCode.DE).toFormattedString();
        boolean ibanExists = accountRepository.findByIban(iban).isPresent();
        if (ibanExists)
            iban =  generateRandomIban();
        log.info("\n:::::::::::::::::::: NEW IBAN GENERATED ::::::::::::::::::::\n");
        return iban.replace("DE", "NG");
    }

    private String generateAccountNumber(){
        final SecureRandom secureRandom = new SecureRandom();
        String prefix = secureRandom.ints(1, 30, 32)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
        String suffix = secureRandom.ints(8, 1, 10)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
        String accountNumber = prefix + suffix;
        if(accountRepository.findByAccountNumber(accountNumber).isPresent())
            accountNumber = generateAccountNumber();
        log.info("\n:::::::::::::::::::: NEW ACCOUNT NUMBER GENERATED ::::::::::::::::::::\n");
        return accountNumber;
    }

    private boolean verifyPin(String pin, String encodedPin){
        return PIN_ENCODER.matches(pin, encodedPin);
    }

    private static CreateAccountResponse getAccountResponse(Account account) {
        return CreateAccountResponse.builder()
                .accountId(account.getId())
                .accountName(account.getAccountName())
                .accountType(account.getAccountType())
                .accountNumber(account.getAccountNumber())
                .build();
    }

    private Customer findCustomerById(Integer customerId){
        return customerRepository.findById(customerId).orElseThrow(
                ()-> new NotFoundException("Customer with the provided id not found"));
    }

    private Account findAccountById(Integer id){
        return accountRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Account not with this id found"));
    }

    private Account findAccountByIban(String iban){
        return accountRepository.findByIban(iban).orElseThrow(
                ()-> new NotFoundException("Account with the provided iban not found"));
    }

    private Account findAccountByAccountNumber(String accountNumber){
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow(
                ()-> new NotFoundException("Account with the provided account number not found"));
    }

    @Override
    public String depositMoney(DepositRequest request) {
        performDeposit(request);
//        sendDepositNotification(account, request.getAmount());
        return"Transaction successful";
    }

    private Account performDeposit(DepositRequest request) {
        Account account = findAccountById(request.getAccountId());
        TransactionType transactionType = TransactionType.CREDIT;
        BigDecimal amount =
                getTransactionMultiplier(transactionType)
                        .multiply(request.getAmount());
        Transaction transaction = setTransaction(amount, transactionType);
        account.getTransactions().add(transaction);
        return accountRepository.save(account);
    }

    private static Transaction setTransaction(BigDecimal amount, TransactionType transactionType) {
        Transaction transaction = new Transaction();
        transaction.setTransactionAmount(amount);
        transaction.setTransactionType(transactionType);
        transaction.setTransactionTime(LocalDateTime.now());
        return transaction;
    }

    private BigDecimal getTransactionMultiplier(TransactionType transactionType){
        return BigDecimal.valueOf(TransactionType.DEBIT == transactionType ? -1 : 1);
    }

    @Override
    public String withdrawMoney(WithdrawRequest request) {
        performWithdraw(request);
//        sendWithdrawNotification(account, request.getAmount);
        return "Transaction successful";
    }

    private Account performWithdraw(WithdrawRequest request) {
        Account account = findAccountById(request.getAccountId());
        checkIfBalanceIsSufficient(request.getAccountId(), request.getAmount());
        validatePin(request.getPin(), account.getAccountPin());
        TransactionType transactionType = TransactionType.DEBIT;
        BigDecimal amount = getTransactionMultiplier(transactionType)
                .multiply(request.getAmount());
        Transaction transaction = setTransaction(amount, transactionType);
        account.getTransactions().add(transaction);
        return accountRepository.save(account);
    }

    private void checkIfBalanceIsSufficient(Integer accountId, BigDecimal amount) {
        BigDecimal balance = calculateBalance(accountId);
        if(amount.compareTo(balance) > 0)
            throw new MyBankException("Insufficient balance");
    }

    private void validatePin(String inputPin, String accountPin) {
        if(!verifyPin(inputPin, accountPin))
            throw new InvalidCredentialException("Pin is incorrect");
    }


    @Override
    public String transferMoney(TransferRequest request) {
        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountId(request.getAccountId());
        withdrawRequest.setAmount(request.getAmount());
        withdrawRequest.setPin(request.getPin());
        Account senderAccount = performWithdraw(withdrawRequest);

        Account recipientAccount = findAccountByAccountNumber(request.getRecipientAccountNumber());
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAmount(request.getAmount());
        depositRequest.setAccountId(recipientAccount.getId());
        performDeposit(depositRequest);
        return "Transaction successful";
    }

    @Override
    public String getBalance(Integer accountId, String pin) {
        Account account = findAccountById(accountId);
        validatePin(pin, account.getAccountPin());
        return formatAmountToNaira(calculateBalance(accountId));
    }

    private BigDecimal calculateBalance(Integer accountId){
        Account account = findAccountById(accountId);
        if (account.getTransactions() == null) {
            return BigDecimal.ZERO;
        }
        return account.getTransactions().stream()
                .map(Transaction::getTransactionAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private String formatAmountToNaira(BigDecimal amount){
        return NumberFormat
                .getCurrencyInstance(Locale.of("en", "NG"))
                .format(amount);
    }

    private TransactionResponse mapTransactionToTransactionResponse(Transaction transaction){
        return TransactionResponse.builder()
                .transactionAmount(changeAmountToNaira(transaction.getTransactionAmount()))
                .transactionType(transaction.getTransactionType())
                .transactionTime(changeDateTimeToString(transaction.getTransactionTime()))
                .build();
    }

    private String changeAmountToNaira(BigDecimal amount){
        return "₦%s".formatted(amount);
    }

    private String changeDateTimeToString(LocalDateTime dateTime){
        return  DateTimeFormatter.ofPattern("EEEE, dd/MM/yy, hh:mm:ss a").format(dateTime);
    }

    @Override
    public List<TransactionResponse> getAccountTransactions(Integer accountId) {
        Account account = findAccountById(accountId);
        return account.getTransactions().stream()
                .map(this::mapTransactionToTransactionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponse> getTransactionByAccountIdAndTimeRange(TransactionByTimeRangeRequest request) {
        return findAccountById(request.getAccountId()).getTransactions()
                .stream()
                .filter(t ->
                        t.getTransactionTime().isAfter(request.getStartTime()) &&
                        t.getTransactionTime().isBefore(request.getEndTime()))
                .map(this::mapTransactionToTransactionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccountByAccountAndCustomerId(Integer accountId, Integer customerId) {
        accountRepository.deleteByIdAndCustomerId(accountId, customerId);
    }

    @Override
    public void deleteAllAccountByCustomerId(Integer customerId) {
        accountRepository.deleteAllByCustomerId(customerId);
    }

    @Override
    public void deleteAllAccounts() {
        accountRepository.deleteAll();
    }

    @Override
    public long numberOfCustomerAccounts(Integer customerId) {
        return accountRepository.countAccountByCustomerId(customerId);
    }

    @Override
    public long numberOfAccounts() {
        return accountRepository.count();
    }

    private void sendDepositNotification(Account account, BigDecimal amount){
        AppUser appUser = account.getCustomer().getAppUser();
        String firstName = appUser.getFirstName();
        String accountName = account.getAccountName();
        String accountNumber = starAccountNumber(account.getAccountNumber());
        String description = "Deposit into account";
        String transactionAmount = formatAmountToNaira(amount);
        String transactionTime = changeDateTimeToString(LocalDateTime.now());
        String currentBalance = formatAmountToNaira(calculateBalance(account.getId()));
        String branchPhoneNumber = "[Your branch phone number]";
        String branchEmailAddress = "[Your branch email address]";
        String htmlContent = String.format(GET_DEPOSIT_MAIL_TEMPLATE, firstName, accountName,
                accountNumber, description, transactionAmount, transactionTime, currentBalance,
                branchPhoneNumber, branchEmailAddress);
        String subject = "Credit Alert Notification";
        mailService.sendMail(appUser.getFirstName(), appUser.getLastName(), subject, htmlContent);
    }


    private String starAccountNumber(String accountNumber){
        return new StringBuilder(accountNumber)
                .replace(2,8, "********")
                .toString();
    }
//  firstName
//  accountName
//  accountNumber
//  description
//  transactionAmount
//  transactionDateAndTime
//  Current balance
//  Branch phone number
//  Branch email address
}