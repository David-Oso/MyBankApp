package com.bank.MyBankApp.account.dto.request;

import com.bank.MyBankApp.account.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateAccountRequest {
    private Integer customerId;
//    private String accountName;
    private AccountType accountType;
    private String pin;
}
