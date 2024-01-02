package com.bank.MyBankApp.customer.repoistory;

import com.bank.MyBankApp.account.model.Account;
import com.bank.MyBankApp.bank.model.Bank;
import com.bank.MyBankApp.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByAppUserEmail(String email);
    boolean existsByAppUserEmail(String email);
    Optional<Customer> findByAppUserPhoneNumber(String phoneNumber);
    boolean existsByAppUserPhoneNumber(String phoneNumber);
    Optional<Customer> findByNin(String nin);
    boolean existsByNin(String nin);
    Optional<Customer> findByBvn(String bvn);
    boolean existsByBvn(String bvn);
    @Query("""
            select c from Customer c\s
            join fetch c.accounts\s
            where c.id =:customerId
            """)
    List<Account> getAllAccountsByCustomerId(Integer customerId);
}
