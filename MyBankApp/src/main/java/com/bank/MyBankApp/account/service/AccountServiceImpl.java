package com.bank.MyBankApp.account.service;

import com.bank.MyBankApp.account.dto.response.TransactionResponse;
import com.bank.MyBankApp.account.model.Account;
import com.bank.MyBankApp.account.repository.AccountRepository;
import com.bank.MyBankApp.account.dto.request.CreateAccountRequest;
import com.bank.MyBankApp.account.dto.request.DepositRequest;
import com.bank.MyBankApp.account.dto.request.TransferRequest;
import com.bank.MyBankApp.account.dto.request.WithdrawRequest;
import com.bank.MyBankApp.exception.InvalidCredentialException;
import com.bank.MyBankApp.exception.MyBankException;
import com.bank.MyBankApp.exception.NotFoundException;
import com.bank.MyBankApp.transaction.model.Transaction;
import com.bank.MyBankApp.transaction.model.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{
    private static final PasswordEncoder PIN_ENCODER =
            Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    private final AccountRepository accountRepository;


    @Override
    public Account createNewAccount(CreateAccountRequest request) {
        Account account = new Account();
        account.setAccountName(request.getAccountName());
        account.setAccountPin(hashPin(request.getPin()));
        account.setIban(generateRandomIban());
        account.setAccountType(request.getAccountType());
        return accountRepository.save(account);
    }

    private static String hashPin(String pin) {
        return PIN_ENCODER.encode(pin);
    }

    private  String generateRandomIban() {
    String iban = Iban.random(CountryCode.DE).toFormattedString();
    boolean ibanExists = accountRepository.findByIban(iban).isPresent();
    if (ibanExists) {
        return generateRandomIban();
    }
    log.info("\n:::::::::::::::::::: NEW IBAN GENERATED ::::::::::::::::::::\n");
    return iban;
    }

    private boolean verifyPin(String pin, String encodedPin){
        return PIN_ENCODER.matches(pin, encodedPin);
    }

    private Account getAccountById(Integer id){
        return accountRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Account not with this id found"));
    }

    private Account getAccountByIban(String iban){
        return accountRepository.findByIban(iban).orElseThrow(
                ()-> new NotFoundException("Account with the provided iban not found"));
    }

    @Override
    public TransactionResponse depositMoney(DepositRequest request) {
        Account account = getAccountById(request.getAccountId());
        TransactionType transactionType = TransactionType.CREDIT;
        BigDecimal amount =
                getTransactionMultiplier(transactionType)
                        .multiply(request.getAmount());
        Transaction transaction = setTransaction(amount, transactionType);
        account.getTransactions().add(transaction);
        accountRepository.save(account);
        BigDecimal balance = calculateBalance(request.getAccountId());
        return getTransactionResponse(account.getAccountName(), account.getIban(), null,
                transactionType, request.getAmount(), transaction.getTransactionTime(), balance);
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

    private static TransactionResponse getTransactionResponse(String accountName, String iban,
        String recipientIban, TransactionType transactionType, BigDecimal amount, LocalDateTime time, BigDecimal balance){
        String mIban = new StringBuilder(iban)
                .replace(8, 21, "* **** **** *").toString();
        String rIban ="";
        if(recipientIban != null){
            rIban = new StringBuilder(recipientIban)
                .replace(8, 21, "* **** **** *").toString();
        }
        String transactionTime = DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy, hh:mm:ss a").format(time);
        return TransactionResponse.builder()
                .accountName(accountName)
                .iban(mIban)
                .recipientIban(rIban)
                .transactionType(transactionType)
                .transactionAmount("₦%s".formatted(amount))
                .transactionTime(transactionTime)
                .currentBalance("₦%s".formatted(balance))
                .build();
    }

    @Override
    public TransactionResponse withdrawMoney(WithdrawRequest request) {
        Account account = getAccountById(request.getAccountId());
        checkIfBalanceIsSufficient(request.getAccountId(), request.getAmount());
        validatePin(request.getPin(), account.getAccountPin());
        TransactionType transactionType = TransactionType.DEBIT;
        BigDecimal amount = getTransactionMultiplier(transactionType)
                .multiply(request.getAmount());
        Transaction transaction = setTransaction(amount, transactionType);
        account.getTransactions().add(transaction);
        accountRepository.save(account);
        BigDecimal balance = calculateBalance(request.getAccountId());
        return getTransactionResponse(account.getAccountName(), account.getIban(), null,
                transactionType, request.getAmount(), transaction.getTransactionTime(), balance);
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
    public TransactionResponse transferMoney(TransferRequest request) {
        Account senderAccount = getAccountById(request.getAccountId());
        Account receiverAccount = getAccountByIban(request.getRecipientIban());
        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountId(request.getAccountId());
        withdrawRequest.setAmount(request.getAmount());
        withdrawRequest.setPin(request.getPin());
        withdrawMoney(withdrawRequest);

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountId(receiverAccount.getId());
        depositRequest.setAmount(request.getAmount());
        depositMoney(depositRequest);
        BigDecimal balance = calculateBalance(request.getAccountId());
        return getTransactionResponse(senderAccount.getAccountName(), senderAccount.getIban(), receiverAccount.getIban(),
                TransactionType.DEBIT, request.getAmount(), LocalDateTime.now(), balance);
    }

    @Override
    public BigDecimal getBalance(Integer accountId, String pin) {
        Account account = getAccountById(accountId);
        validatePin(pin, account.getAccountPin());
        return calculateBalance(accountId);
    }

    private BigDecimal calculateBalance(Integer accountId){
        Account account = getAccountById(accountId);
        if (account.getTransactions() == null) {
            return BigDecimal.ZERO;
        }
        return account.getTransactions().stream()
                .map(Transaction::getTransactionAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void deleteAllAccounts() {
        accountRepository.deleteAll();
    }

    @Override
    public long numberOfAccounts() {
        return accountRepository.count();
    }
}