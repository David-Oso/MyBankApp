package com.bank.MyBankApp.customer.service;

import com.bank.MyBankApp.customer.dto.Request.RegisterCustomerRequest;
import com.bank.MyBankApp.customer.dto.Response.RegisterCustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    @Override
    public RegisterCustomerResponse registerCustomer(RegisterCustomerRequest request) {
        return null;
    }
}
