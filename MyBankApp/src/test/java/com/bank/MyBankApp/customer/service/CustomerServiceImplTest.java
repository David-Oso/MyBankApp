package com.bank.MyBankApp.customer.service;

import com.bank.MyBankApp.customer.dto.Request.RegisterCustomerRequest;
import com.bank.MyBankApp.customer.dto.Response.RegisterCustomerResponse;
import com.bank.MyBankApp.loan.model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest

@ActiveProfiles("test")
//@TestPropertySource(locations = "classpath:application-test.yaml")
class CustomerServiceImplTest {
    @Autowired CustomerService customerService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void registerCustomer() {
        RegisterCustomerRequest request =new RegisterCustomerRequest();
        request.setDateOfBirth("21/01/2002");
        request.setEmail("temx@email.com");
        request.setPhoneNumber("09078900458");
        request.setGender(Gender.MALE);
        request.setLastName("Temz");
        request.setFirstName("Femz");
        request.setMiddleName("Remz");
        RegisterCustomerResponse response = customerService.registerCustomer(request);
        assertThat(response.getCustomerId()).isEqualTo(1);
    }

    @Test
    void addCustomerAddress() {

    }

    @Test
    void addNextOfKin() {
    }
}