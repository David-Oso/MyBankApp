package com.bank.MyBankApp.customer.service;

import com.bank.MyBankApp.appUser.dto.request.ChangePasswordRequest;
import com.bank.MyBankApp.appUser.dto.response.ChangePasswordResponse;
import com.bank.MyBankApp.customer.dto.request.AddAddressRequest;
import com.bank.MyBankApp.customer.dto.request.LoginRequest;
import com.bank.MyBankApp.customer.dto.request.RegisterCustomerRequest;
import com.bank.MyBankApp.customer.dto.request.UploadImageRequest;
import com.bank.MyBankApp.customer.dto.response.CustomerResponse;
import com.bank.MyBankApp.customer.dto.response.LoginResponse;
import com.bank.MyBankApp.customer.dto.response.RegisterCustomerResponse;
import org.springframework.data.domain.Page;

import java.security.Principal;


public interface CustomerService {
    RegisterCustomerResponse registerCustomer(RegisterCustomerRequest request);
    String addCustomerAddress(AddAddressRequest request, Integer customerId);
//    verifyEmail
    LoginResponse login(LoginRequest loginRequest);
    CustomerResponse getCustomerById(Integer customerId);
    CustomerResponse getCustomerByEmail(String email);
    CustomerResponse getCustomerByPhoneNumber(String phoneNumber);
    CustomerResponse getCustomerByNin(String nin);
    CustomerResponse getCustomerByBvn(String bvn);
    ChangePasswordResponse changePassword(ChangePasswordRequest request, Principal user);
    String uploadImage(UploadImageRequest request);
    Page<CustomerResponse> getAllCustomers(int pageNumber);
    void deleteByCustomerId(Integer customerId);
    void deleteAll();
    long count();
}
