package com.bank.MyBankApp.customer.service;

import com.bank.MyBankApp.appUser.repository.AppUserRepository;
import com.bank.MyBankApp.customer.dto.Request.AddCustomerAddressRequest;
import com.bank.MyBankApp.customer.dto.Request.AddNextOfKinRequest;
import com.bank.MyBankApp.customer.dto.Request.RegisterCustomerRequest;
import com.bank.MyBankApp.customer.dto.Response.RegisterCustomerResponse;
import com.bank.MyBankApp.loan.model.Gender;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest

@ActiveProfiles("test")
class CustomerServiceImplTest {
    @Autowired
    CustomerService customerService;
    @Autowired
    AppUserRepository appUserRepository;
    private RegisterCustomerRequest registerCustomerRequest1;
    private RegisterCustomerRequest registerCustomerRequest2;
    private RegisterCustomerRequest registerCustomerRequest3;
    private RegisterCustomerRequest registerCustomerRequest4;

    private AddCustomerAddressRequest addressRequest1;
    private AddCustomerAddressRequest addressRequest2;
    private AddCustomerAddressRequest addressRequest3;
    private AddCustomerAddressRequest addressRequest4;

    private AddNextOfKinRequest nextOfKinRequest1;
    private AddNextOfKinRequest nextOfKinRequest2;
    private AddNextOfKinRequest nextOfKinRequest3;
    private AddNextOfKinRequest nextOfKinRequest4;

    @BeforeEach
    void setUp() {
        registerCustomerRequest1 = new RegisterCustomerRequest();
        registerCustomerRequest1.setDateOfBirth("21/01/2002");
        registerCustomerRequest1.setEmail("temx@email.com");
        registerCustomerRequest1.setPhoneNumber("09078900458");
        registerCustomerRequest1.setGender(Gender.MALE);
        registerCustomerRequest1.setLastName("Temz");
        registerCustomerRequest1.setFirstName("Femz");
        registerCustomerRequest1.setMiddleName("Remz");
        registerCustomerRequest1.setBvn("12345678901");
        registerCustomerRequest1.setNin("09876543210");

        registerCustomerRequest2 = new RegisterCustomerRequest();
        registerCustomerRequest2.setDateOfBirth("22/01/2002");
        registerCustomerRequest2.setEmail("remi@email.com");
        registerCustomerRequest2.setPhoneNumber("08078956458");
        registerCustomerRequest2.setGender(Gender.FEMALE);
        registerCustomerRequest2.setLastName("Remi");
        registerCustomerRequest2.setFirstName("Rhoda");
        registerCustomerRequest2.setMiddleName("Rolis");
        registerCustomerRequest2.setBvn("21345678956");
        registerCustomerRequest2.setNin("59876643211");

        registerCustomerRequest3 = new RegisterCustomerRequest();
        registerCustomerRequest3.setDateOfBirth("23/01/2002");
        registerCustomerRequest3.setEmail("saheed@email.com");
        registerCustomerRequest3.setPhoneNumber("08178900478");
        registerCustomerRequest3.setGender(Gender.MALE);
        registerCustomerRequest3.setLastName("Saheed");
        registerCustomerRequest3.setFirstName("Salah");
        registerCustomerRequest3.setMiddleName("Sola");
        registerCustomerRequest3.setBvn("32345078905");
        registerCustomerRequest3.setNin("29876643219");

        registerCustomerRequest4 = new RegisterCustomerRequest();
        registerCustomerRequest4.setDateOfBirth("24/01/2002");
        registerCustomerRequest4.setEmail("glory@email.com");
        registerCustomerRequest4.setPhoneNumber("08090900898");
        registerCustomerRequest4.setGender(Gender.FEMALE);
        registerCustomerRequest4.setLastName("Glory");
        registerCustomerRequest4.setFirstName("Gift");
        registerCustomerRequest4.setMiddleName("gifted");
        registerCustomerRequest4.setBvn("62345378909");
        registerCustomerRequest4.setNin("19876903212");


        addressRequest1 = new AddCustomerAddressRequest();
        addressRequest1.setStreetNumber(1);
        addressRequest1.setStreetName("Herbert Macaulay way");
        addressRequest1.setTownName("Sabo");
        addressRequest1.setCityName("Yaba");
        addressRequest1.setState("Lagos");
        addressRequest1.setCountry("Nigeria");

        addressRequest2 = new AddCustomerAddressRequest();
        addressRequest2.setStreetNumber(2);
        addressRequest2.setStreetName("Herbert Macaulay way");
        addressRequest2.setTownName("Sabo");
        addressRequest2.setCityName("Yaba");
        addressRequest2.setState("Lagos");
        addressRequest2.setCountry("Nigeria");

        addressRequest3 = new AddCustomerAddressRequest();
        addressRequest3.setStreetNumber(3);
        addressRequest3.setStreetName("Herbert Macaulay way");
        addressRequest3.setTownName("Sabo");
        addressRequest3.setCityName("Yaba");
        addressRequest3.setState("Lagos");
        addressRequest3.setCountry("Nigeria");

        addressRequest4 = new AddCustomerAddressRequest();
        addressRequest4.setStreetNumber(4);
        addressRequest4.setStreetName("Herbert Macaulay way");
        addressRequest4.setTownName("Sabo");
        addressRequest4.setCityName("Yaba");
        addressRequest4.setState("Lagos");
        addressRequest4.setCountry("Nigeria");
    }

    @AfterEach
    void tearDown(){
        appUserRepository.deleteAll();
        customerService.deleteAll();
    }

    @Test
    void registerCustomer() {
        RegisterCustomerResponse response = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(response.getCustomerId()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("Registration successful");
    }

    @Test
    void addCustomerAddress() {
        RegisterCustomerResponse response = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(response.getCustomerId()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("Registration successful");

        String addAddressResponse = customerService.AddCustomerAddress(addressRequest1, 1);
        assertThat(addAddressResponse).isEqualTo("Address added successfully");
    }

    @Test
    void addNextOfKin() {
    }

//    @AfterAll
//    static void tearDownAfterAll(){
////        delete all the transactions from the database.
//    }
}