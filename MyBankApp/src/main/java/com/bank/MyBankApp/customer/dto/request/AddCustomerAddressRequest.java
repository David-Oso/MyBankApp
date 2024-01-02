package com.bank.MyBankApp.customer.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.bank.MyBankApp.utilities.ValidationUtils.ADDRESS_REGEX;
import static com.bank.MyBankApp.utilities.ValidationUtils.STREET_NUMBER_REGEX;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddCustomerAddressRequest {
    @NotNull(message = "field street number cannot be null")
    @Positive(message = "street number must be positive")
    @Pattern(regexp = STREET_NUMBER_REGEX, message = "street number ")
    private Integer streetNumber;

    @NotBlank(message = "field street name cannot be blank")
    @Pattern(regexp = ADDRESS_REGEX, message = "street name must only contain letters")
    private String streetName;

    @NotBlank(message = "field town name cannot be blank")
    @Pattern(regexp = ADDRESS_REGEX, message = "town name must only contain letters")
    private String townName;

    @NotBlank(message = "field ciy name cannot be blank")
    @Pattern(regexp = ADDRESS_REGEX, message = "city name must only contain letters")
    private String cityName;

    @NotBlank(message = "field state cannot be blank")
    @Pattern(regexp = ADDRESS_REGEX, message = "state must only contain letters")
    private String state;

    @NotBlank(message = "field country cannot be blank")
    @Pattern(regexp = ADDRESS_REGEX, message = "country name must only contain letters")
    private String country;
}
