package com.bank.MyBankApp.account.service;

import com.bank.MyBankApp.account.request.CreateNewAccountRequest;
import com.bank.MyBankApp.account.response.CreateNewAccountResponse;

public interface AccountService {
    CreateNewAccountResponse createNewAccount(CreateNewAccountRequest request);
}
