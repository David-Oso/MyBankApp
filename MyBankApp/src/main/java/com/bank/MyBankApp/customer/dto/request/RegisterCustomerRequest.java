package com.bank.MyBankApp.customer.dto.request;

import com.bank.MyBankApp.customer.model.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    private AddAppUserRequest addAppUserRequest;
    @NotNull(message = "field gender cannot be null")
    private Gender gender;

    @NotBlank(message = "field date of birth cannot be blank")
    @DateTimeFormat(pattern = DATE_OF_BIRTH_REGEX)
    private String dateOfBirth;

    @NotBlank(message = "field bvn cannot be blank")
    @Pattern(regexp = BVN_REGEX, message = "bvn must be 11 digits")
    private String bvn;

    @NotBlank(message = "field nin cannot be blank")
    @Pattern(regexp = NIN_REGEX, message = "nin must be 11 digits")
    private String nin;
}
