package com.bank.MyBankApp.nextOfKin.model;

import com.bank.MyBankApp.address.model.Address;
import com.bank.MyBankApp.loan.model.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "next_of_kins")
public class NextOfkin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender nextOfKinGender;
    private LocalDate dateOfBirth;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
}
