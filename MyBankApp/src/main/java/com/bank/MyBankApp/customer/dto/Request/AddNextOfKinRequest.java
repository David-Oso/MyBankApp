package com.bank.MyBankApp.customer.dto.Request;

import com.bank.MyBankApp.loan.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddNextOfKinRequest {
    private String firstName;
    private String lastName;
    private Gender nextOfKinGender;
    private LocalDate dateOfBirth;
//    next of kin address.
    private Integer streetNumber;
    private String streetName;
    private String townName;
    private String cityName;
    private String state;
    private String country;
}
