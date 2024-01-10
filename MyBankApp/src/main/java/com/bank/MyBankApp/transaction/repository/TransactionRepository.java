package com.bank.MyBankApp.transaction.repository;

import com.bank.MyBankApp.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
