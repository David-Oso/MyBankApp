package com.bank.MyBankApp.customer.model;

import com.bank.MyBankApp.address.model.Address;
import com.bank.MyBankApp.loan.model.Gender;
import com.bank.MyBankApp.appUser.model.AppUser;
import com.bank.MyBankApp.nextOfKin.model.NextOfkin;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(cascade = {CascadeType.ALL, CascadeType.DETACH}, fetch = FetchType.LAZY, orphanRemoval = true)
    private AppUser appUser;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(unique= true)
    private String nin;
    @Column(unique= true)
    private String bvn;
    private LocalDate dateOfBirth;
    private int age;
    private String imageUrl;
    @OneToOne(cascade = {CascadeType.ALL, CascadeType.DETACH}, fetch = FetchType.LAZY, orphanRemoval = true)
    private Address address;
    @OneToOne(cascade = {CascadeType.ALL, CascadeType.DETACH}, fetch = FetchType.LAZY, orphanRemoval = true)
    private NextOfkin nextOfkin;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
}
