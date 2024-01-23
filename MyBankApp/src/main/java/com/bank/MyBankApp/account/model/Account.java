package com.bank.MyBankApp.account.model;

import com.bank.MyBankApp.branch.model.Branch;
import com.bank.MyBankApp.customer.model.Customer;
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

    @Column(unique = true)
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private String accountPin;
    @OneToMany(cascade = {CascadeType.ALL, CascadeType.DETACH}, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;
}
