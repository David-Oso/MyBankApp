package com.bank.MyBankApp.customer.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.bank.MyBankApp.utilities.ValidationUtils.*;
import static com.bank.MyBankApp.utilities.ValidationUtils.PHONE_NUMBER_REGEX;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddAppUserRequest {
    @NotBlank(message = "field first name cannot be blank")
    @Pattern(regexp = NAME_REGEX, message = "name must start with capital letter")
    private String firstName;

    @NotBlank(message = "field middle name cannot be blank")
    @Pattern(regexp = NAME_REGEX, message = "name must start with capital letter")
    private String middleName;

    @NotBlank(message = "field last name cannot be blank")
    @Pattern(regexp = NAME_REGEX, message = "name must start with capital letter")
    private String lastName;

    @NotBlank(message = "field email cannot be blank")
    @Email(regexp = EMAIL_REGEX, message = "enter a valid email")
    private String email;

    @NotBlank(message = "field password cannot be blank")
    @Pattern(regexp = PASSWORD_REGEX, message = "Password must " +
            "contain at least one capital letter, one small letter, a number and special character(@$!%*?&)")
    private String password;

    @NotBlank(message = "field phone number cannot be blank")
    @Pattern(regexp = PHONE_NUMBER_REGEX, message = "invalid phone number")
    private String phoneNumber;
}
