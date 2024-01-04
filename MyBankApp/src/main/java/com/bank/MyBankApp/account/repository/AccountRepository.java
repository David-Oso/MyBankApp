package com.bank.MyBankApp.account.repository;

import com.bank.MyBankApp.account.model.Account;
import com.bank.MyBankApp.account.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByIban(String iban);
    boolean existsByAccountTypeAndCustomerId(AccountType accountType, Integer customerId);
    void deleteByIdAndCustomerId(Integer accountId, Integer customerId);
    long countAccountByCustomerId(Integer customerId);
}
