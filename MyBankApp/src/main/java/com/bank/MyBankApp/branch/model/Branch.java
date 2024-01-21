package com.bank.MyBankApp.branch.model;

import com.bank.MyBankApp.account.model.Account;
import com.bank.MyBankApp.address.model.Address;
import com.bank.MyBankApp.appUser.model.AppUser;
import com.bank.MyBankApp.customer.model.Customer;
import com.bank.MyBankApp.loan.model.Loan;
import com.bank.MyBankApp.utilities.MyBankAppUtils;
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
    private final String branchName = MyBankAppUtils.BANK_NAME;
    @Column(unique = true)
    private String branchNumber;
    @OneToOne(cascade = {CascadeType.ALL, CascadeType.DETACH}, fetch = FetchType.LAZY)
    private AppUser appUser;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Address branchAddress;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loan> loans;

}
