package com.bank.MyBankApp.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WithdrawRequest {
    private Integer accountId;
    private BigDecimal amount;
    private String pin;
}
