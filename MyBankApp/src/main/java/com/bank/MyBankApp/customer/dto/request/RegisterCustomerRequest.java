package com.bank.MyBankApp.customer.dto.request;

import com.bank.MyBankApp.customer.model.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import static com.bank.MyBankApp.utilities.ValidationUtils.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterCustomerRequest {
    @NotNull(message = "field first name cannot be null")
    @NotEmpty(message = "field first name cannot be empty")
    @NotBlank(message = "field first name cannot be blank")
    @Pattern(regexp = NAME_REGEX, message = "name must start with capital letter")
    private String firstName;

    @NotNull(message = "field middle name cannot be null")
    @NotEmpty(message = "field middle name cannot be empty")
    @NotBlank(message = "field middle name cannot be blank")
    @Pattern(regexp = NAME_REGEX, message = "name must start with capital letter")
    private String middleName;

    @NotNull(message = "field last name cannot be null")
    @NotEmpty(message = "field last name cannot be empty")
    @NotBlank(message = "field last name cannot be blank")
    @Pattern(regexp = NAME_REGEX, message = "name must start with capital letter")
    private String lastName;

    @NotNull(message = "field email cannot be null")
    @NotEmpty(message = "field email cannot be empty")
    @NotBlank(message = "field email cannot be blank")
    @Pattern(regexp = EMAIL_REGEX, message = "invalid email")
    private String email;

    @NotNull(message = "field password cannot be null")
    @NotEmpty(message = "field password cannot be empty")
    @NotBlank(message = "field password cannot be blank")
    @Pattern(regexp = PASSWORD_REGEX, message = "Password must " +
            "contain at least one capital letter, one small letter, a number and special character(@$!%*?&)")
    private String password;

    @NotNull(message = "field phone number cannot be null")
    @NotEmpty(message = "field phone number cannot be empty")
    @NotBlank(message = "field phone number cannot be blank")
    @Pattern(regexp = PHONE_NUMBER_REGEX, message = "invalid phone number")
    private String phoneNumber;

    @NotNull(message = "field gender cannot be null")
     private Gender gender;

    @NotNull(message = "field date of birth cannot be null")
    @NotEmpty(message = "field date of birth cannot be empty")
    @NotBlank(message = "field date of birth cannot be blank")
//    @Pattern(regexp = DATE_OF_BIRTH_REGEX, message = "date must be in the format dd/mm/yyyy")
    @DateTimeFormat(pattern = DATE_OF_BIRTH_REGEX)
    private String dateOfBirth;

    @NotNull(message = "field bvn cannot be null")
    @NotEmpty(message = "field bvn cannot be empty")
    @NotBlank(message = "field bvn cannot be blank")
    @Pattern(regexp = BVN_REGEX, message = "bvn must be 11 digits")
    private String bvn;

    @NotNull(message = "field nin cannot be null")
    @NotEmpty(message = "field nin cannot be empty")
    @NotBlank(message = "field nin cannot be blank")
    @Pattern(regexp = NIN_REGEX, message = "nin must be 11 digits")
    private String nin;
}
