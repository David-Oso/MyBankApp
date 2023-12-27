package com.bank.MyBankApp.customer.service;

import com.bank.MyBankApp.customer.dto.Request.AddCustomerAddressRequest;
import com.bank.MyBankApp.customer.dto.Request.AddNextOfKinRequest;
import com.bank.MyBankApp.customer.dto.Request.RegisterCustomerRequest;
import com.bank.MyBankApp.customer.dto.Response.CustomerResponse;
import com.bank.MyBankApp.customer.dto.Response.RegisterCustomerResponse;
import org.springframework.data.domain.Page;

public interface CustomerService {
    RegisterCustomerResponse registerCustomer(RegisterCustomerRequest request);
    String addCustomerAddress(AddCustomerAddressRequest request, Integer customerId);
    String addNextOfKin(AddNextOfKinRequest request, Integer customerId);
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
