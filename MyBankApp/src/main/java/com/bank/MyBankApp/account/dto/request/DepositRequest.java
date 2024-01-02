package com.bank.MyBankApp.account.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepositRequest {
    @NotNull(message = "field account id cannot be null")
    private Integer accountId;

    @NotNull(message = "field amount cannot be null")
    @DecimalMin(value = "1.00", message = "Amount must be greater than or equal to ₦1.00")
    @DecimalMax(value = "2000000.00", message = "Amount must be lesser than or equal to ₦2,000,000.00")
    private BigDecimal amount;
}
