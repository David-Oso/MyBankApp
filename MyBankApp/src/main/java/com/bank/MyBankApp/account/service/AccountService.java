package com.bank.MyBankApp.account.service;

import com.bank.MyBankApp.account.dto.response.CreateAccountResponse;
import com.bank.MyBankApp.account.dto.response.TransactionResponse;
import com.bank.MyBankApp.account.dto.request.CreateAccountRequest;
import com.bank.MyBankApp.account.dto.request.DepositRequest;
import com.bank.MyBankApp.account.dto.request.TransferRequest;
import com.bank.MyBankApp.account.dto.request.WithdrawRequest;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    CreateAccountResponse createNewAccount(CreateAccountRequest request);
    String  depositMoney(DepositRequest request);
    String withdrawMoney(WithdrawRequest request);
    String transferMoney(TransferRequest request);
    BigDecimal getBalance(Integer accountId, String pin);
    TransactionResponse getTransactionById(Integer accountId, Integer transactionId);
    List<TransactionResponse> getAccountTransactions(Integer accountId);
    void deleteAccountByAccountAndCustomerId(Integer accountId, Integer customerId);
    void deleteAllAccountByCustomerId(Integer customerId);
    void deleteAllAccounts();
    long numberOfCustomerAccounts(Integer customerId);
    long numberOfAccounts();
}
