package com.bank.MyBankApp.customer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddCustomerAddressRequest {
    private Integer streetNumber;
    private String streetName;
    private String townName;
    private String cityName;
    private String state;
    private String country;
}
