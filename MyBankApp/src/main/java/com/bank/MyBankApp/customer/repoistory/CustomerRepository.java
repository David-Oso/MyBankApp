package com.bank.MyBankApp.customer.repoistory;

import com.bank.MyBankApp.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByAppUserEmail(String email);
    boolean existsByAppUserEmail(String email);
    Customer findByAppUserPhoneNumber(String phoneNumber);
    boolean existsByAppUserPhoneNumber(String phoneNumber);
    Customer findByNin(String nin);
    boolean existsByNin(String nin);
    Customer findByBvn(String bvn);
    boolean existsByBvn(String bvn);
}
