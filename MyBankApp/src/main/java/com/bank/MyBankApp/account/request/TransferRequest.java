package com.bank.MyBankApp.account.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransferRequest {
    private Integer accountId;
    private Integer recipientAccountId;
    private BigDecimal amount;
    private String pin;
}
