package com.bank.MyBankApp.account.service;

import com.bank.MyBankApp.account.model.Account;
import com.bank.MyBankApp.account.model.AccountType;
import com.bank.MyBankApp.account.repository.AccountRepository;
import com.bank.MyBankApp.account.request.CreateAccountRequest;
import com.bank.MyBankApp.account.request.DepositRequest;
import com.bank.MyBankApp.account.request.TransferRequest;
import com.bank.MyBankApp.account.request.WithdrawRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    @Override
    public boolean verifyPin(String pin, String encodedPin){
        return PIN_ENCODER.matches(pin, encodedPin);
    }

    @Override
    public String depositMoney(DepositRequest request) {
        return null;
    }

    @Override
    public String withdrawMoney(WithdrawRequest request) {
        return null;
    }

    @Override
    public String transferMoney(TransferRequest request) {
        return null;
    }

    @Override
    public BigDecimal getBalance(Integer accountId) {
        return null;
    }
}