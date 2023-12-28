package com.bank.MyBankApp.customer.dto.request;

import com.bank.MyBankApp.account.model.AccountType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateAccountResponse {
    private String message;
    private AccountType accountType;
    private String accountName;
    private String accountNumber;
}
