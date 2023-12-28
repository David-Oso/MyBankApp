package com.bank.MyBankApp.customer.dto.request;

import com.bank.MyBankApp.customer.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterCustomerRequest {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private Gender gender;
    private String dateOfBirth;
    private String bvn;
    private String nin;
}
