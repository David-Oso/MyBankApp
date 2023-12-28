package com.bank.MyBankApp.account.service;

import com.bank.MyBankApp.account.model.Account;
import com.bank.MyBankApp.account.request.CreateAccountRequest;

public interface AccountService {
    Account createNewAccount(CreateAccountRequest request);
}
