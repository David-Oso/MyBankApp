package com.bank.MyBankApp.account.dto.request;

import com.bank.MyBankApp.account.model.AccountType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.bank.MyBankApp.utilities.ValidationUtils.PIN_REGEX;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateAccountRequest {
    @NotNull(message = "field customer id cannot be null")
    @Positive(message = "customer id must be positive")
    private Integer customerId;

    @NotNull(message = "field account type cannot be null")
    private AccountType accountType;

    @NotBlank(message = "field pin cannot be blank")
    @Pattern(regexp = PIN_REGEX, message = "pin must be 4 digits")
    private String pin;
}
