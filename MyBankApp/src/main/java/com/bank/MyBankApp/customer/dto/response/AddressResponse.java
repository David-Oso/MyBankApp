package com.bank.MyBankApp.customer.dto.response;

import lombok.*;

//@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AddressResponse {
    private Integer streetNumber;
    private String streetName;
    private String townName;
    private String cityName;
    private String state;
    private String country;
}
