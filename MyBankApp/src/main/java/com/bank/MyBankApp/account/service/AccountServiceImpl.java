package com.bank.MyBankApp.account.service;

import com.bank.MyBankApp.account.model.Account;
import com.bank.MyBankApp.account.model.AccountType;
import com.bank.MyBankApp.account.repository.AccountRepository;
import com.bank.MyBankApp.account.request.CreateAccountRequest;
import com.bank.MyBankApp.account.request.DepositRequest;
import com.bank.MyBankApp.account.request.TransferRequest;
import com.bank.MyBankApp.account.request.WithdrawRequest;
import com.bank.MyBankApp.exception.InvalidCredentialException;
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
import java.util.List;

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

//    @Override
    private boolean verifyPin(String pin, String encodedPin){
        return PIN_ENCODER.matches(pin, encodedPin);
    }

    private Account getAccountById(Integer id){
        return accountRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Account not with this id found"));
    }

    @Override
    public String depositMoney(DepositRequest request) {
        Account account = getAccountById(request.getAccountId());
        BigDecimal amount =
                getTransactionMultiplier(TransactionType.CREDIT)
                        .multiply(request.getAmount());
        Transaction transaction = setTransaction(amount, TransactionType.CREDIT);
        account.getTransactions().add(transaction);
        accountRepository.save(account);
        return "Transaction successful";
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
        Account account = getAccountById(request.getAccountId());
        validatePin(request.getPin(), account.getAccountPin());
        BigDecimal amount = getTransactionMultiplier(TransactionType.DEBIT)
                .multiply(request.getAmount());
        Transaction transaction = setTransaction(amount, TransactionType.DEBIT);
        account.getTransactions().add(transaction);
        accountRepository.save(account);
        return "Transaction successful";
    }

    private void validatePin(String inputPin, String accountPin) {
        if(!verifyPin(inputPin, accountPin))
            throw new InvalidCredentialException("Pin is incorrect");
    }


    @Override
    public String transferMoney(TransferRequest request) {
        return null;
    }

    @Override
    public BigDecimal getBalance(Integer accountId, String pin) {
        Account account = getAccountById(accountId);
        validatePin(pin, account.getAccountPin());
        return calculateBalance(accountId);
    }

    private BigDecimal calculateBalance(Integer accountId){
        Account account = getAccountById(accountId);
        List<Transaction> transactions = account.getTransactions();
        BigDecimal balance = BigDecimal.ZERO;
        for(Transaction transaction : transactions){
            balance = balance.add(transaction.getTransactionAmount());
        }
        return balance;
    }

    @Override
    public void deleteAllAccounts() {
        accountRepository.deleteAll();
    }
}