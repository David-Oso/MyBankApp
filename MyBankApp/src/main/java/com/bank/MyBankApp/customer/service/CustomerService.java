package com.bank.MyBankApp.customer.service;

import com.bank.MyBankApp.customer.dto.Request.AddCustomerAddressRequest;
import com.bank.MyBankApp.customer.dto.Request.AddNextOfKinRequest;
import com.bank.MyBankApp.customer.dto.Request.RegisterCustomerRequest;
import com.bank.MyBankApp.customer.dto.Response.RegisterCustomerResponse;

public interface CustomerService {
    RegisterCustomerResponse registerCustomer(RegisterCustomerRequest request);
    String addCustomerAddress(AddCustomerAddressRequest request, Integer customerId);
    String addNextOfKin(AddNextOfKinRequest request, Integer customerId);
    void deleteByCustomerId(Integer customerId);
    void deleteAll();
    long count();
}
