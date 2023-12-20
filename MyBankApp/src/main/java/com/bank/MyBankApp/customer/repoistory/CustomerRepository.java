package com.bank.MyBankApp.customer.repoistory;

import com.bank.MyBankApp.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findCustomerByAppUser_Email(String email);
    Customer findCustomerByAppUser_PhoneNumber(String phoneNumber);
}
