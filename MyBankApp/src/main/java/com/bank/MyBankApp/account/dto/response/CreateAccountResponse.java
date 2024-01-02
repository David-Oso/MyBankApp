package com.bank.MyBankApp.account.dto.response;

import com.bank.MyBankApp.account.model.AccountType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateAccountResponse {
    private AccountType accountType;
    private String accountName;
    private String iban;
}
