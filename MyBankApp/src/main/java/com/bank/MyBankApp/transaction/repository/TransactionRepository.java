package com.bank.MyBankApp.transaction.repository;

import com.bank.MyBankApp.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
//        @Query("SELECT t FROM Transaction t JOIN t.account a WHERE a.id = :accountId AND t.transactionTime BETWEEN :startTime AND :endTime")
//    List<Transaction> findTransactionsByAccountIdAndTimeRange(
//            @Param("accountId") Integer accountId,
//            @Param("startTime") LocalDateTime startTime,
//            @Param("endTime") LocalDateTime endTime
//    );
}
