package com.bank.MyBankApp.customer.model;

import com.bank.MyBankApp.address.model.Address;
import com.bank.MyBankApp.loan.model.Gender;
import com.bank.MyBankApp.model.AppUser;
import com.bank.MyBankApp.nextOfKin.model.NextOfkin;
import com.bank.MyBankApp.transaction.model.Transaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AppUser appUser;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate dateOfBirth;
    private int age;
    private String imageUrl;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Address address;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private NextOfkin nextOfkin;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
}
