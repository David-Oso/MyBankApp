package com.bank.MyBankApp.bank.repository;

import com.bank.MyBankApp.bank.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Integer> {
    Bank findByBankCode(String bankCode);
}
