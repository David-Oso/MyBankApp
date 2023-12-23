package com.bank.MyBankApp.mail.mailRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Recipient {
    private String name;
    private String email;
}
