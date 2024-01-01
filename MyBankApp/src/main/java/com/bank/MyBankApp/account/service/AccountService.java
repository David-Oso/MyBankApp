package com.bank.MyBankApp.account.service;

import com.bank.MyBankApp.account.dto.response.TransactionResponse;
import com.bank.MyBankApp.account.model.Account;
import com.bank.MyBankApp.account.dto.request.CreateAccountRequest;
import com.bank.MyBankApp.account.dto.request.DepositRequest;
import com.bank.MyBankApp.account.dto.request.TransferRequest;
import com.bank.MyBankApp.account.dto.request.WithdrawRequest;

import java.math.BigDecimal;

public interface AccountService {
    Account createNewAccount(CreateAccountRequest request);
    TransactionResponse depositMoney(DepositRequest request);
    TransactionResponse withdrawMoney(WithdrawRequest request);
    TransactionResponse transferMoney(TransferRequest request);
    BigDecimal getBalance(Integer accountId, String pin);
    void deleteAllAccounts();
    long numberOfAccounts();
}
