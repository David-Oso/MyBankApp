package com.bank.MyBankApp.account.dto.request;

import com.bank.MyBankApp.account.model.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    private Integer customerId;

    @NotNull(message = "field account type cannot be null")
    @NotEmpty(message = "field account type cannot be empty")
    @NotBlank(message = "field account type cannot be blank")
    private AccountType accountType;


    @NotNull(message = "field pin cannot be  null")
    @NotBlank(message = "field pin cannot be blank")
    @NotEmpty(message = "field pin cannot be empty")
    @Pattern(regexp = PIN_REGEX, message = "pin must be 4 digits")
    private String pin;
}
