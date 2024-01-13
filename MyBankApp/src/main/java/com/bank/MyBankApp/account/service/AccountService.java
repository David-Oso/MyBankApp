package com.bank.MyBankApp.account.service;

import com.bank.MyBankApp.account.dto.request.*;
import com.bank.MyBankApp.account.dto.response.CreateAccountResponse;
import com.bank.MyBankApp.account.dto.response.TransactionResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface AccountService {
    CreateAccountResponse createNewAccount(CreateAccountRequest request);
    String  depositMoney(DepositRequest request);
    String withdrawMoney(WithdrawRequest request);
    String transferMoney(TransferRequest request);
    String getBalance(Integer accountId, String pin);
    List<TransactionResponse> getAccountTransactions(Integer accountId);
    List<TransactionResponse> getTransactionByAccountIdAndTimeRange(TransactionByTimeRangeRequest request);
    void deleteAccountByAccountAndCustomerId(Integer accountId, Integer customerId);
    void deleteAllAccountByCustomerId(Integer customerId);
    void deleteAllAccounts();
    long numberOfCustomerAccounts(Integer customerId);
    long numberOfAccounts();
}
