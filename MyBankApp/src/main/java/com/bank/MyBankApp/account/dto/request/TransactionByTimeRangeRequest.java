package com.bank.MyBankApp.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionByTimeRangeRequest {
    private Integer accountId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
