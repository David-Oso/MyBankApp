package com.bank.MyBankApp.bank.model;

import com.bank.MyBankApp.address.model.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bank")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String bankCode;
    private String name;
    private String bankPhoneNumber;
    private String branchEmail;
    @OneToOne(cascade = CascadeType.ALL)
    private Address bankAddress;
}
