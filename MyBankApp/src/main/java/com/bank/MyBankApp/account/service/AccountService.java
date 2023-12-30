package com.bank.MyBankApp.account.service;

import com.bank.MyBankApp.account.model.Account;
import com.bank.MyBankApp.account.request.CreateAccountRequest;
import com.bank.MyBankApp.account.request.DepositRequest;
import com.bank.MyBankApp.account.request.TransferRequest;
import com.bank.MyBankApp.account.request.WithdrawRequest;

import java.math.BigDecimal;

public interface AccountService {
    Account createNewAccount(CreateAccountRequest request);
    boolean verifyPin(String pin, String encodedPin);
    String depositMoney(DepositRequest request);
    String withdrawMoney(WithdrawRequest request);
    String transferMoney(TransferRequest request);
    BigDecimal getBalance(Integer accountId);
    void deleteAllAccounts();
}
