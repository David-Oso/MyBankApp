package com.bank.MyBankApp.customer.dto.Response;

import com.bank.MyBankApp.address.model.Address;
import com.bank.MyBankApp.loan.model.Gender;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomerResponse {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private String dateOfBirth;
    private int age;
    private String imageUrl;
    private Address address;
}