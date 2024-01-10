package com.bank.MyBankApp.transaction.repository;

import com.bank.MyBankApp.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("""
        select t from Account a
        join a.transactions t
        where a.id = :accountId and t.id = : transactionId
        """)
    Transaction findTransactionByAccountIdAndTransactionId(Integer accountId, Integer transactionId);
}
