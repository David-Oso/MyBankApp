package com.bank.MyBankApp.customer.service;

import com.bank.MyBankApp.address.model.Address;
import com.bank.MyBankApp.appUser.dto.request.ChangePasswordRequest;
import com.bank.MyBankApp.appUser.dto.response.ChangePasswordResponse;
import com.bank.MyBankApp.appUser.dto.response.JwtResponse;
import com.bank.MyBankApp.appUser.model.Role;
import com.bank.MyBankApp.appUser.service.AppUserService;
import com.bank.MyBankApp.cloudinary.CloudinaryService;
import com.bank.MyBankApp.customer.dto.request.*;
import com.bank.MyBankApp.customer.dto.response.*;
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
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final AppUserService appUserService;
    private final CloudinaryService cloudinaryService;

    @Override
    public RegisterCustomerResponse registerCustomer(RegisterCustomerRequest request) {
        checkIfCustomerExistsByEmail(request.getAddAppUserRequest().getEmail());
        checkIfCustomerExistsByPhoneNumber(request.getAddAppUserRequest().getPhoneNumber());
        checkIfCustomerExistsByNin(request.getNin());
        checkIfCustomerExistsByBvn(request.getBvn());
        AppUser appUser = setAppUser(request.getAddAppUserRequest());
        Customer customer = modelMapper.map(request, Customer.class);
        LocalDate dateOfBirth = changeDateStringToLocalDate(request.getDateOfBirth());
        int age = getAgeFromDate(dateOfBirth);
        customer.setAppUser(appUser);
        customer.setDateOfBirth(dateOfBirth);
        customer.setAge(age);
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

    private AppUser setAppUser(AddAppUserRequest request) {
        AppUser appUser = modelMapper.map(request, AppUser.class);
        appUser.setRole(Role.CUSTOMER);
        appUser.setPassword(appUserService.encodePassword(request.getPassword()));
        return appUser;
    }

    private static LocalDate changeDateStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date.trim(), formatter);
    }
    private static int getAgeFromDate(LocalDate date) {
        return Period.between(date, LocalDate.now()).getYears();
    }

    private RegisterCustomerResponse getRegisterCustomerResponse(Customer customer){
        return RegisterCustomerResponse.builder()
                .customerId(customer.getId())
                .message("Registration successful")
                .build();
    }

    @Override
    public String addCustomerAddress(AddAddressRequest request, Integer customerId) {
        Customer customer = customerById(customerId);
        Address address = modelMapper.map(request, Address.class);
        customer.setAddress(address);
        customerRepository.save(customer);
        return "Address added successfully";
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        AppUser appUser = appUserService.authenticate(request.getEmail(), request.getPassword());
//        checkIfAppUserIsEnabled(appUser);
        appUserService.revokeAllUserTokens(appUser);
        JwtResponse jwtResponse = appUserService.generateJwtToken(appUser);
        return LoginResponse.builder()
                .message("Login successful")
                .jwtResponse(jwtResponse)
                .build();
    }

    private static void checkIfAppUserIsEnabled(AppUser appUser) {
        if(!appUser.isEnabled())
            throw new MyBankException("App user must be enabled before he can login.");
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
    public ChangePasswordResponse changePassword(ChangePasswordRequest request, Principal user) {
        return appUserService.changePassword(request, user);
    }

    @Override
    public String uploadImage(UploadImageRequest request) {
        Customer customer = customerById(request.getCustomerId());
        String imageUrl = cloudinaryService.upload(request.getProfileImage());
        customer.setImageUrl(imageUrl);
        customer.setUpdatedAt(LocalDateTime.now());
        customerRepository.save(customer);
        return "Profile image uploaded";
    }

    @Override
    public Page<CustomerResponse> getAllCustomers(int pageNumber) {
        int page = pageNumber < 1 ? 0 : pageNumber - 1;
        Pageable pageable = PageRequest.of(page, NUMBER_OF_ITEMS_PER_PAGE);
        Page<Customer> customers = customerRepository.findAll(pageable);

        List<CustomerResponse> customerResponses = customers
                .getContent()
                .stream()
                .map(this::getCustomerResponse)
                .toList();
        return new PageImpl<>(customerResponses, pageable, customers.getTotalElements());
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
        AddressResponse addressResponse = modelMapper.map(customer.getAddress(), AddressResponse.class);
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
                .addressResponse(addressResponse)
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
