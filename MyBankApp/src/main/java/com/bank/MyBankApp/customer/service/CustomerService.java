package com.bank.MyBankApp.customer.service;

import com.bank.MyBankApp.customer.dto.Request.RegisterCustomerRequest;
import com.bank.MyBankApp.customer.dto.Response.RegisterCustomerResponse;

public interface CustomerService {
    RegisterCustomerResponse registerCustomer(RegisterCustomerRequest request);
}
