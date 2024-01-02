package com.bank.MyBankApp.customer.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.bank.MyBankApp.utilities.ValidationUtils.EMAIL_REGEX;
import static com.bank.MyBankApp.utilities.ValidationUtils.PASSWORD_REGEX;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "field email cannot be blank")
    @Pattern(regexp = EMAIL_REGEX, message = "invalid email")
    private String email;

    @NotBlank(message = "field password cannot be blank")
    @Pattern(regexp = PASSWORD_REGEX, message = "Password must " +
            "contain at least one capital letter, one small letter, a number and special character(@$!%*?&)")
    private String password;}
