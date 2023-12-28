package com.bank.MyBankApp.account.service;

import com.bank.MyBankApp.account.model.Account;
import com.bank.MyBankApp.account.repository.AccountRepository;
import com.bank.MyBankApp.account.request.CreateNewAccountRequest;
import com.bank.MyBankApp.account.response.CreateNewAccountResponse;
import com.bank.MyBankApp.appUser.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;


    @Override
    public CreateNewAccountResponse createNewAccount(CreateNewAccountRequest request) {
        return null;
    }
}
