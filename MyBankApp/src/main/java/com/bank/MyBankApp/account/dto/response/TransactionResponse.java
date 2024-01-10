package com.bank.MyBankApp.account.dto.response;

import com.bank.MyBankApp.transaction.model.TransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {
    private TransactionType transactionType;
    private String transactionAmount;
    private String transactionTime;
}
