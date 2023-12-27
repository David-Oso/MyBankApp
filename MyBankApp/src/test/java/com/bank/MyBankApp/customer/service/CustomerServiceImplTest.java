package com.bank.MyBankApp.customer.service;

import com.bank.MyBankApp.customer.dto.Request.AddCustomerAddressRequest;
import com.bank.MyBankApp.customer.dto.Request.AddNextOfKinRequest;
import com.bank.MyBankApp.customer.dto.Request.RegisterCustomerRequest;
import com.bank.MyBankApp.customer.dto.Response.CustomerResponse;
import com.bank.MyBankApp.customer.dto.Response.RegisterCustomerResponse;
import com.bank.MyBankApp.exception.NotFoundException;
import com.bank.MyBankApp.loan.model.Gender;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CustomerServiceImplTest {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    CustomerService customerService;

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

        nextOfKinRequest1 = new AddNextOfKinRequest();
        nextOfKinRequest1.setFirstName("Samuel");
        nextOfKinRequest1.setLastName("Shola");
        nextOfKinRequest1.setGender(Gender.MALE);
        nextOfKinRequest1.setDateOfBirth("20/04/2000");

        nextOfKinRequest2 = new AddNextOfKinRequest();
        nextOfKinRequest2.setFirstName("Favour");
        nextOfKinRequest2.setLastName("Mbata");
        nextOfKinRequest2.setGender(Gender.FEMALE);
        nextOfKinRequest2.setDateOfBirth("20/04/2000");

        nextOfKinRequest3 = new AddNextOfKinRequest();
        nextOfKinRequest3.setFirstName("Joyce");
        nextOfKinRequest3.setLastName("Glory");
        nextOfKinRequest3.setGender(Gender.FEMALE);
        nextOfKinRequest3.setDateOfBirth("20/04/2000");

        nextOfKinRequest4 = new AddNextOfKinRequest();
        nextOfKinRequest4.setFirstName("Babatunde");
        nextOfKinRequest4.setLastName("Ishola");
        nextOfKinRequest4.setGender(Gender.MALE);
        nextOfKinRequest4.setDateOfBirth("20/04/2000");
    }

    @AfterEach
    void tearDown(){
        customerService.deleteAll();
        entityManager.createNativeQuery("ALTER TABLE my_bank_app_testdb.customers AUTO_INCREMENT = 1").executeUpdate();
    }

    @Test
    void registerCustomerTest() {
        RegisterCustomerResponse response = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(response.getCustomerId()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("Registration successful");
    }

    @Test
    void addCustomerAddressTest() {
        RegisterCustomerResponse response = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(response.getCustomerId()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("Registration successful");

        String addAddressResponse = customerService.addCustomerAddress(addressRequest1, 1);
        assertThat(addAddressResponse).isEqualTo("Address added successfully");
    }

    @Test
    void addNextOfKinTest() {
        RegisterCustomerResponse response = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(response.getCustomerId()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("Registration successful");

        String addAddressResponse = customerService.addCustomerAddress(addressRequest1, 1);
        assertThat(addAddressResponse).isEqualTo("Address added successfully");

        String nextOfKinResponse = customerService.addNextOfKin(nextOfKinRequest1, 1);
        assertThat(nextOfKinResponse).isEqualTo("Next of kin added successfully");
    }

    @Test
    void getCustomerByIdTest(){
        RegisterCustomerResponse response = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(response.getCustomerId()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("Registration successful");

        String addAddressResponse = customerService.addCustomerAddress(addressRequest1, 1);
        assertThat(addAddressResponse).isEqualTo("Address added successfully");

        String nextOfKinResponse = customerService.addNextOfKin(nextOfKinRequest1, 1);
        assertThat(nextOfKinResponse).isEqualTo("Next of kin added successfully");

        CustomerResponse customerResponse = customerService.getCustomerById(1);
        assertThat(customerResponse.getFirstName()).isEqualTo(registerCustomerRequest1.getFirstName());
        assertThat(customerResponse.getMiddleName()).isEqualTo(registerCustomerRequest1.getMiddleName());
        assertThat(customerResponse.getLastName()).isEqualTo(registerCustomerRequest1.getLastName());
        assertThat(customerResponse.getEmail()).isEqualTo(registerCustomerRequest1.getEmail());
        assertThat(customerResponse.getPhoneNumber()).isEqualTo(registerCustomerRequest1.getPhoneNumber());
        assertThat(customerResponse.getGender()).isEqualTo(registerCustomerRequest1.getGender());
        assertThat(customerResponse.getDateOfBirth()).isEqualTo(registerCustomerRequest1.getDateOfBirth());
        assertThat(customerResponse.getAddress()).isNotNull();
    }

    @Test
    void getCustomerByEmailTest(){
        RegisterCustomerResponse response = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(response.getCustomerId()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("Registration successful");

        String addAddressResponse = customerService.addCustomerAddress(addressRequest1, 1);
        assertThat(addAddressResponse).isEqualTo("Address added successfully");

        String nextOfKinResponse = customerService.addNextOfKin(nextOfKinRequest1, 1);
        assertThat(nextOfKinResponse).isEqualTo("Next of kin added successfully");

        CustomerResponse customerResponse = customerService.getCustomerByEmail("temx@email.com");
        assertThat(customerResponse.getFirstName()).isEqualTo(registerCustomerRequest1.getFirstName());
        assertThat(customerResponse.getMiddleName()).isEqualTo(registerCustomerRequest1.getMiddleName());
        assertThat(customerResponse.getLastName()).isEqualTo(registerCustomerRequest1.getLastName());
        assertThat(customerResponse.getEmail()).isEqualTo(registerCustomerRequest1.getEmail());
        assertThat(customerResponse.getPhoneNumber()).isEqualTo(registerCustomerRequest1.getPhoneNumber());
        assertThat(customerResponse.getGender()).isEqualTo(registerCustomerRequest1.getGender());
        assertThat(customerResponse.getDateOfBirth()).isEqualTo(registerCustomerRequest1.getDateOfBirth());
        assertThat(customerResponse.getAddress()).isNotNull();
    }

  @Test
    void getCustomerByPhoneNumberTest(){
        RegisterCustomerResponse response = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(response.getCustomerId()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("Registration successful");

        String addAddressResponse = customerService.addCustomerAddress(addressRequest1, 1);
        assertThat(addAddressResponse).isEqualTo("Address added successfully");

        String nextOfKinResponse = customerService.addNextOfKin(nextOfKinRequest1, 1);
        assertThat(nextOfKinResponse).isEqualTo("Next of kin added successfully");

        CustomerResponse customerResponse = customerService.getCustomerByPhoneNumber("09078900458");
        assertThat(customerResponse.getFirstName()).isEqualTo(registerCustomerRequest1.getFirstName());
        assertThat(customerResponse.getMiddleName()).isEqualTo(registerCustomerRequest1.getMiddleName());
        assertThat(customerResponse.getLastName()).isEqualTo(registerCustomerRequest1.getLastName());
        assertThat(customerResponse.getEmail()).isEqualTo(registerCustomerRequest1.getEmail());
        assertThat(customerResponse.getPhoneNumber()).isEqualTo(registerCustomerRequest1.getPhoneNumber());
        assertThat(customerResponse.getGender()).isEqualTo(registerCustomerRequest1.getGender());
        assertThat(customerResponse.getDateOfBirth()).isEqualTo(registerCustomerRequest1.getDateOfBirth());
        assertThat(customerResponse.getAddress()).isNotNull();
    }

  @Test
    void getCustomerByPhoneNumberThrowsExceptionTest(){
        RegisterCustomerResponse response = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(response.getCustomerId()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("Registration successful");

        String addAddressResponse = customerService.addCustomerAddress(addressRequest1, 1);
        assertThat(addAddressResponse).isEqualTo("Address added successfully");

        String nextOfKinResponse = customerService.addNextOfKin(nextOfKinRequest1, 1);
        assertThat(nextOfKinResponse).isEqualTo("Next of kin added successfully");

        NotFoundException exception = assertThrows(NotFoundException.class,
                ()-> customerService.getCustomerByPhoneNumber("09078900459tomer by phone"));
        assertThat(exception.getMessage()).isEqualTo("Customer with this phone number not found");
    }

    @Test
    void deleteByCustomerIdTest(){
        RegisterCustomerResponse response = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(response.getCustomerId()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("Registration successful");

        String addAddressResponse = customerService.addCustomerAddress(addressRequest1, 1);
        assertThat(addAddressResponse).isEqualTo("Address added successfully");

        String nextOfKinResponse = customerService.addNextOfKin(nextOfKinRequest1, 1);
        assertThat(nextOfKinResponse).isEqualTo("Next of kin added successfully");

        assertThat(customerService.count()).isEqualTo(1);
        customerService.deleteByCustomerId(1);
        assertThat(customerService.count()).isEqualTo(0);
    }



    @Test
    void deleteAllCustomerTest(){
        RegisterCustomerResponse response = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(response.getCustomerId()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("Registration successful");
        String addAddressResponse = customerService.addCustomerAddress(addressRequest1, 1);
        assertThat(addAddressResponse).isEqualTo("Address added successfully");
        String nextOfKinResponse = customerService.addNextOfKin(nextOfKinRequest1, 1);
        assertThat(nextOfKinResponse).isEqualTo("Next of kin added successfully");
        assertThat(customerService.count()).isEqualTo(1);

        RegisterCustomerResponse response2 = customerService.registerCustomer(registerCustomerRequest2);
        assertThat(response2.getCustomerId()).isEqualTo(2);
        assertThat(response2.getMessage()).isEqualTo("Registration successful");
        String addAddressResponse2 = customerService.addCustomerAddress(addressRequest2, 2);
        assertThat(addAddressResponse2).isEqualTo("Address added successfully");
        String nextOfKinResponse2 = customerService.addNextOfKin(nextOfKinRequest2, 2);
        assertThat(nextOfKinResponse2).isEqualTo("Next of kin added successfully");
        assertThat(customerService.count()).isEqualTo(2);

        RegisterCustomerResponse response3 = customerService.registerCustomer(registerCustomerRequest3);
        assertThat(response3.getCustomerId()).isEqualTo(3);
        assertThat(response3.getMessage()).isEqualTo("Registration successful");
        String addAddressResponse3 = customerService.addCustomerAddress(addressRequest3, 3);
        assertThat(addAddressResponse3).isEqualTo("Address added successfully");
        String nextOfKinResponse3 = customerService.addNextOfKin(nextOfKinRequest3, 3);
        assertThat(nextOfKinResponse3).isEqualTo("Next of kin added successfully");
        assertThat(customerService.count()).isEqualTo(3);

        RegisterCustomerResponse response4 = customerService.registerCustomer(registerCustomerRequest4);
        assertThat(response4.getCustomerId()).isEqualTo(4);
        assertThat(response4.getMessage()).isEqualTo("Registration successful");
        String addAddressResponse4 = customerService.addCustomerAddress(addressRequest4, 4);
        assertThat(addAddressResponse4).isEqualTo("Address added successfully");
        String nextOfKinResponse4 = customerService.addNextOfKin(nextOfKinRequest4, 4);
        assertThat(nextOfKinResponse4).isEqualTo("Next of kin added successfully");
        assertThat(customerService.count()).isEqualTo(4);

        customerService.deleteAll();
        assertThat(customerService.count()).isEqualTo(0);
    }
}