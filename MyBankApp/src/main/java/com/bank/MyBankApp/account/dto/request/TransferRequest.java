package com.bank.MyBankApp.account.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import static com.bank.MyBankApp.utilities.ValidationUtils.IBAN_REGEX;
import static com.bank.MyBankApp.utilities.ValidationUtils.PIN_REGEX;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransferRequest {
    @NotNull(message = "field account id cannot be null")
    @Positive(message = "account id must be positive")
    private Integer accountId;

    @NotNull(message = "field recipient iban cannot be null")
    @NotBlank(message = "field recipient iban cannot be blank")
    @NotEmpty(message = "field recipient iban cannot be empty")
    @Pattern(regexp = IBAN_REGEX, message = "iban must be DE## #### #### #### #### ##")
    private String recipientIban;

    @NotNull(message = "field amount cannot be null")
    @DecimalMin(value = "1.00", message = "Amount must be greater than or equal to ₦1.00")
    @DecimalMax(value = "2000000.00", message = "Amount must be lesser than or equal to ₦2,000,000.00")
    private BigDecimal amount;

    @NotNull(message = "field pin cannot be null")
    @NotBlank(message = "field pin cannot be blank")
    @NotEmpty(message = "field pin cannot be empty")
    @Pattern(regexp = PIN_REGEX, message = "Enter your 4 digits pin")
    private String pin;
}
