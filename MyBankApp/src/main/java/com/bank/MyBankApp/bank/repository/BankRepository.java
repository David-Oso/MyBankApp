package com.bank.MyBankApp.bank.repository;

import com.bank.MyBankApp.bank.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Integer> {
//    @Query("SELECT b FROM Bank b JOIN FETCH b.bankAddress WHERE b.id = :bankId")
    Optional<Bank> findByBankCode(String bankCode);
    @Query("SELECT b FROM Bank b JOIN FETCH b.bankAddress WHERE b.id = :bankId")
    Optional<Bank> findBankById(@Param("bankId") Integer bankId);
}
