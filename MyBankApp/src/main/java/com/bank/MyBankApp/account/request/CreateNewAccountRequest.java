package com.bank.MyBankApp.account.request;

import com.bank.MyBankApp.account.model.AccountType;
import com.bank.MyBankApp.customer.model.Customer;
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
    private Customer customer;
}
