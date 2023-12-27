package com.bank.MyBankApp.customer.repoistory;

import com.bank.MyBankApp.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
