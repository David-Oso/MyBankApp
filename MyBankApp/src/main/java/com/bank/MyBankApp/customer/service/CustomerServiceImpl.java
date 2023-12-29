package com.bank.MyBankApp.customer.service;

import com.bank.MyBankApp.address.model.Address;
import com.bank.MyBankApp.customer.dto.request.AddCustomerAddressRequest;
import com.bank.MyBankApp.customer.dto.request.LoginRequest;
import com.bank.MyBankApp.customer.dto.request.RegisterCustomerRequest;
import com.bank.MyBankApp.customer.dto.response.CustomerResponse;
import com.bank.MyBankApp.customer.dto.response.LoginResponse;
import com.bank.MyBankApp.customer.dto.response.RegisterCustomerResponse;
import com.bank.MyBankApp.customer.model.Customer;
import com.bank.MyBankApp.customer.repoistory.CustomerRepository;
import com.bank.MyBankApp.appUser.model.AppUser;
import com.bank.MyBankApp.exception.AlreadyExistsException;
import com.bank.MyBankApp.exception.MyBankException;
import com.bank.MyBankApp.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.bank.MyBankApp.utilities.MyBankAppUtils.NUMBER_OF_ITEMS_PER_PAGE;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterCustomerResponse registerCustomer(RegisterCustomerRequest request) {
        checkIfCustomerExistsByEmail(request.getEmail());
        checkIfCustomerExistsByPhoneNumber(request.getPhoneNumber());
        checkIfCustomerExistsByNin(request.getNin());
        checkIfCustomerExistsByBvn(request.getBvn());
        AppUser appUser = modelMapper.map(request, AppUser.class);
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));
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
    public LoginResponse login(LoginRequest loginRequest) {
        Customer customer = customerByEmail(loginRequest.getEmail());
        AppUser appUser = customer.getAppUser();
        if(!(appUser.getPassword().equals(loginRequest.getPassword())))
            throw new MyBankException("Incorrect password");
        return LoginResponse.builder()
                .message("Login successful")
                .build();
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
        Customer customer = customerByNin(nin);
        return getCustomerResponse(customer);
    }

    @Override
    public CustomerResponse getCustomerByBvn(String bvn) {
        Customer customer = customerByBvn(bvn);
        return getCustomerResponse(customer);
    }

    @Override
    public Page<CustomerResponse> getAllCustomers(int pageNumber) {
        int page = pageNumber < 1 ? 0 : pageNumber -1;
        Pageable pageable = PageRequest.of(page, NUMBER_OF_ITEMS_PER_PAGE);
        Page<Customer> customers = customerRepository.findAll(pageable);

        List<CustomerResponse> customerResponses = customers
                .getContent()
                .stream()
                .map(this::getCustomerResponse)
                .toList();
        return new PageImpl<>(customerResponses, pageable, customers.getTotalElements());
//        return null;

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

    private Customer customerByNin(String nin){
        return customerRepository.findByNin(nin).orElseThrow(
                ()-> new NotFoundException("Customer with this nin not found"));
    }

    private Customer customerByBvn(String bvn){
        return customerRepository.findByBvn(bvn).orElseThrow(
                ()-> new NotFoundException("Customer with this bvn not found"));
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
