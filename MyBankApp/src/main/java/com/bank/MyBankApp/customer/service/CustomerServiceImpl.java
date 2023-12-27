package com.bank.MyBankApp.customer.service;

import com.bank.MyBankApp.address.model.Address;
import com.bank.MyBankApp.customer.dto.Request.AddCustomerAddressRequest;
import com.bank.MyBankApp.customer.dto.Request.AddNextOfKinRequest;
import com.bank.MyBankApp.customer.dto.Request.RegisterCustomerRequest;
import com.bank.MyBankApp.customer.dto.Response.CustomerResponse;
import com.bank.MyBankApp.customer.dto.Response.RegisterCustomerResponse;
import com.bank.MyBankApp.customer.model.Customer;
import com.bank.MyBankApp.customer.repoistory.CustomerRepository;
import com.bank.MyBankApp.appUser.model.AppUser;
import com.bank.MyBankApp.exception.AlreadyExistsException;
import com.bank.MyBankApp.exception.NotFoundException;
import com.bank.MyBankApp.nextOfKin.model.NextOfkin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
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
        log.info("\n::::::::::::::::::::  CREATED NEW CUSTOMER  ::::::::::::::::::::\n");
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
    public String addCustomerAddress(AddCustomerAddressRequest request, Integer customerId) {
        Customer customer = customerById(customerId);
        Address address = modelMapper.map(request, Address.class);
        customer.setAddress(address);
        customerRepository.save(customer);
        return "Address added successfully";
    }

    @Override
    public String addNextOfKin(AddNextOfKinRequest request, Integer customerId) {
        Customer customer = customerById(customerId);
        NextOfkin nextOfkin = modelMapper.map(request, NextOfkin.class);
        LocalDate dateOfBirth = changeDateStringToLocalDate(request.getDateOfBirth());
        nextOfkin.setDateOfBirth(dateOfBirth);
        customer.setNextOfkin(nextOfkin);
        customerRepository.save(customer);
        return "Next of kin added successfully";
    }

    @Override
    public CustomerResponse getCustomerById(Integer customerId) {
        Customer customer = customerById(customerId);
        return getCustomerResponse(customer);
    }

    @Override
    public CustomerResponse getCustomerByEmail(String email) {
        Customer customer = customerByEmail(email);
        return getCustomerResponse(customer);
    }

    @Override
    public CustomerResponse getCustomerByPhoneNumber(String phoneNumber) {
        Customer customer  = customerByPhoneNumber(phoneNumber);
        return getCustomerResponse(customer);
    }

    @Override
    public CustomerResponse getCustomerByNin(String nin) {
        return null;
    }

    @Override
    public CustomerResponse getCustomerByBvn(String bvn) {
        return null;
    }

    @Override
    public Page<CustomerResponse> getAllCustomers(int pageNumber) {
        return null;
    }

    private Customer customerById(Integer id){
        return customerRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Customer with this id not found."));
    }

    private Customer customerByEmail(String email){
        return customerRepository.findByAppUserEmail(email).orElseThrow(
                ()-> new NotFoundException("Customer with this email not found."));
    }

    private Customer customerByPhoneNumber(String phoneNumber){
        return customerRepository.findByAppUserPhoneNumber(phoneNumber).orElseThrow(
                ()-> new NotFoundException("Customer with this phone number not found"));
    }

    private CustomerResponse getCustomerResponse(Customer customer){
        Address address = modelMapper.map(customer.getAddress(), Address.class);
        AppUser appUser = customer.getAppUser();
        String dateInString = changeLocalDateToString(customer.getDateOfBirth());
        return CustomerResponse.builder()
                .firstName(appUser.getFirstName())
                .middleName(appUser.getMiddleName())
                .lastName(appUser.getLastName())
                .email(appUser.getEmail())
                .phoneNumber(appUser.getPhoneNumber())
                .gender(customer.getGender())
                .imageUrl(customer.getImageUrl())
                .age(customer.getAge())
                .dateOfBirth(dateInString)
                .address(address)
                .build();
    }

    private static String changeLocalDateToString(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    @Override
    public void deleteByCustomerId(Integer customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public void deleteAll() {
        customerRepository.deleteAll();
    }

    @Override
    public long count() {
        return customerRepository.count();
    }

}
