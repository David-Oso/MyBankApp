package com.bank.MyBankApp.customer.service;

import com.bank.MyBankApp.customer.dto.Request.AddCustomerAddressRequest;
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
    @Autowired CustomerService customerService;
    private RegisterCustomerRequest registerCustomerRequest1;
    private RegisterCustomerRequest registerCustomerRequest2;
    private RegisterCustomerRequest registerCustomerRequest3;
    private RegisterCustomerRequest registerCustomerRequest4;

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

        registerCustomerRequest2 = new RegisterCustomerRequest();
        registerCustomerRequest2.setDateOfBirth("21/01/2002");
        registerCustomerRequest2.setEmail("temx@email.com");
        registerCustomerRequest2.setPhoneNumber("09078900458");
        registerCustomerRequest2.setGender(Gender.MALE);
        registerCustomerRequest2.setLastName("Temz");
        registerCustomerRequest2.setFirstName("Femz");
        registerCustomerRequest2.setMiddleName("Remz");

        registerCustomerRequest3 = new RegisterCustomerRequest();
        registerCustomerRequest3.setDateOfBirth("21/01/2002");
        registerCustomerRequest3.setEmail("temx@email.com");
        registerCustomerRequest3.setPhoneNumber("09078900458");
        registerCustomerRequest3.setGender(Gender.MALE);
        registerCustomerRequest3.setLastName("Temz");
        registerCustomerRequest3.setFirstName("Femz");
        registerCustomerRequest3.setMiddleName("Remz");

        registerCustomerRequest4 = new RegisterCustomerRequest();
        registerCustomerRequest4.setDateOfBirth("21/01/2002");
        registerCustomerRequest4.setEmail("temx@email.com");
        registerCustomerRequest4.setPhoneNumber("09078900458");
        registerCustomerRequest4.setGender(Gender.MALE);
        registerCustomerRequest4.setLastName("Temz");
        registerCustomerRequest4.setFirstName("Femz");
        registerCustomerRequest4.setMiddleName("Remz");

    }

    @AfterEach
    void tearDown(){
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


    }

    @Test
    void addNextOfKin() {
    }

//    @AfterAll
//    static void tearDownAfterAll(){
////        delete all the transactions from the database.
//    }
}