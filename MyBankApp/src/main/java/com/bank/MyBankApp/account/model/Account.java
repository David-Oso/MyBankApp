package com.bank.MyBankApp.account.model;

import com.bank.MyBankApp.transaction.model.Transaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String accountName;
    @Column(unique = true)
    private String iban;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private String accountPin;
    @OneToMany(cascade = {CascadeType.ALL, CascadeType.DETACH}, fetch = FetchType.EAGER)
    private List<Transaction> transactions = new ArrayList<>();
}
