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
    private Gender gender;
    private String dateOfBirth;
}
