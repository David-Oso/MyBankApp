package com.bank.MyBankApp.branch.model;

import com.bank.MyBankApp.account.model.Account;
import com.bank.MyBankApp.address.model.Address;
import com.bank.MyBankApp.loan.model.Loan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "branches")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String branchNumber;
    private String branchName;
    @Column(unique = true)
    private String branchPhoneNumber;
    @Column(unique = true)
    private String branchEmail;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Address branchAddress;
    private boolean isApproved;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loan> loans;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts;
}
