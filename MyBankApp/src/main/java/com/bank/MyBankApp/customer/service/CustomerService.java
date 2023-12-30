package com.bank.MyBankApp.customer.service;

import com.bank.MyBankApp.customer.dto.request.AddCustomerAddressRequest;
import com.bank.MyBankApp.customer.dto.request.LoginRequest;
import com.bank.MyBankApp.customer.dto.request.RegisterCustomerRequest;
import com.bank.MyBankApp.customer.dto.response.CustomerResponse;
import com.bank.MyBankApp.customer.dto.response.LoginResponse;
import com.bank.MyBankApp.customer.dto.response.RegisterCustomerResponse;
import org.springframework.data.domain.Page;

import java.security.Principal;

public interface CustomerService {
    RegisterCustomerResponse registerCustomer(RegisterCustomerRequest request);
    String addCustomerAddress(AddCustomerAddressRequest request, Integer customerId);
//    verifyEmail
    LoginResponse login(LoginRequest loginRequest);
    CustomerResponse getCustomerById(Integer customerId);
    CustomerResponse getCustomerByEmail(String email);
    CustomerResponse getCustomerByPhoneNumber(String phoneNumber);
    CustomerResponse getCustomerByNin(String nin);
    CustomerResponse getCustomerByBvn(String bvn);
    Page<CustomerResponse> getAllCustomers(int pageNumber);
    void deleteByCustomerId(Integer customerId);
    void deleteAll();
    long count();
}
