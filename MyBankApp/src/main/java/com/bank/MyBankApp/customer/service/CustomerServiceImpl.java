package com.bank.MyBankApp.customer.service;

import com.bank.MyBankApp.customer.dto.Request.AddCustomerAddressRequest;
import com.bank.MyBankApp.customer.dto.Request.AddNextOfKinRequest;
import com.bank.MyBankApp.customer.dto.Request.RegisterCustomerRequest;
import com.bank.MyBankApp.customer.dto.Response.RegisterCustomerResponse;
import com.bank.MyBankApp.customer.model.Customer;
import com.bank.MyBankApp.customer.repoistory.CustomerRepository;
import com.bank.MyBankApp.appUser.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public RegisterCustomerResponse registerCustomer(RegisterCustomerRequest request) {
//        check if user already exists
        AppUser appUser = modelMapper.map(request, AppUser.class);
        Customer customer = new Customer();
        LocalDate dateOfBirth = changeDateStringToLocalDate(request.getDateOfBirth());
        int age = changeDateToInt(dateOfBirth);
        customer.setDateOfBirth(dateOfBirth);
        customer.setAge(age);
        customer.setGender(request.getGender());
        customer.setAppUser(appUser);
//        send verification mail
        Customer savedCustomer = customerRepository.save(customer);
        return getRegisterCustomerResponse(savedCustomer);
    }

    @Override
    public String AddCustomerAddress(AddCustomerAddressRequest request) {
        return null;
    }

    @Override
    public String AddNextOfKin(AddNextOfKinRequest request) {
        return null;
    }

    private static LocalDate changeDateStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date.trim(), formatter);
    }
    private static int changeDateToInt(LocalDate date) {
        return Period.between(date, LocalDate.now()).getYears();
    }

    private RegisterCustomerResponse getRegisterCustomerResponse(Customer customer){
        return RegisterCustomerResponse.builder()
                .customerId(customer.getId())
                .message("Registration successful")
                .build();
    }
}
