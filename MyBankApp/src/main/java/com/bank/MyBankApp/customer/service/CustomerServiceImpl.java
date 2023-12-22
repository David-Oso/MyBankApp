package com.bank.MyBankApp.customer.service;

import com.bank.MyBankApp.address.model.Address;
import com.bank.MyBankApp.customer.dto.Request.AddCustomerAddressRequest;
import com.bank.MyBankApp.customer.dto.Request.AddNextOfKinRequest;
import com.bank.MyBankApp.customer.dto.Request.RegisterCustomerRequest;
import com.bank.MyBankApp.customer.dto.Response.RegisterCustomerResponse;
import com.bank.MyBankApp.customer.model.Customer;
import com.bank.MyBankApp.customer.repoistory.CustomerRepository;
import com.bank.MyBankApp.appUser.model.AppUser;
import com.bank.MyBankApp.exception.AlreadyExistsException;
import com.bank.MyBankApp.exception.NotFoundException;
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
        checkIfCustomerExistsByEmail(request.getEmail());
        checkIfCustomerExistsByPhoneNumber(request.getPhoneNumber());
        checkIfCustomerExistsByNin(request.getNin());
        checkIfCustomerExistsByBvn(request.getBvn());
        AppUser appUser = modelMapper.map(request, AppUser.class);
        Customer customer = modelMapper.map(request, Customer.class);
        LocalDate dateOfBirth = changeDateStringToLocalDate(request.getDateOfBirth());
        int age = changeDateToInt(dateOfBirth);
        customer.setDateOfBirth(dateOfBirth);
        customer.setAge(age);
        customer.setAppUser(appUser);
        Customer savedCustomer = customerRepository.save(customer);
        return getRegisterCustomerResponse(savedCustomer);
    }
    private void checkIfCustomerExistsByEmail(String email){
        if(customerRepository.existsByAppUserEmail(email))
            throw new AlreadyExistsException("Customer with this email already exists.");

    }

    private void checkIfCustomerExistsByPhoneNumber(String phoneNumber){
        if(customerRepository.existsByAppUserPhoneNumber(phoneNumber))
            throw new AlreadyExistsException("Customer with this phone number already exists.");
    }

    private void checkIfCustomerExistsByNin(String nin){
        if(customerRepository.existsByNin(nin))
            throw new AlreadyExistsException("Customer with this nin already exists.");
    }

    private void checkIfCustomerExistsByBvn(String bvn){
        if(customerRepository.existsByBvn(bvn))
            throw new AlreadyExistsException("Customer with this bvn already exists.");
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

    @Override
    public String AddCustomerAddress(AddCustomerAddressRequest request, Integer customerId) {
        Customer customer = getCustomerById(customerId);
        Address address = modelMapper.map(request, Address.class);
        customer.setAddress(address);
        customerRepository.save(customer);
        return "Address added successfully";
    }

    @Override
    public String AddNextOfKin(AddNextOfKinRequest request) {
        return null;
    }

    @Override
    public void deleteAll() {
        customerRepository.deleteAll();
    }
    private Customer getCustomerById(Integer id){
        return customerRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Customer with this id not found."));
    }
}
