package com.bank.MyBankApp.customer.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.bank.MyBankApp.utilities.ValidationUtils.ADDRESS_REGEX;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddAddressRequest {
    @NotNull(message = "field street number cannot be null")
    @Positive(message = "street number must be positive")
    @Min(value = 1, message = "Number must be at least 1")
    @Max(value=999, message = "Number must be at most 999")
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
