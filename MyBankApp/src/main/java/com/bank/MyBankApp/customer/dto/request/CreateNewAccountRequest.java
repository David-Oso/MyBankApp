package com.bank.MyBankApp.customer.dto.request;

import com.bank.MyBankApp.account.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateNewAccountRequest {
    private AccountType accountType;
    private String accountPin;
}
