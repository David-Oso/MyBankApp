package com.bank.MyBankApp.customer.service;

import com.bank.MyBankApp.appSecurity.jwtToken.MyBankJwtTokenRepository;
import com.bank.MyBankApp.customer.dto.request.AddAddressRequest;
import com.bank.MyBankApp.customer.dto.request.AddAppUserRequest;
import com.bank.MyBankApp.customer.dto.request.LoginRequest;
import com.bank.MyBankApp.customer.dto.request.RegisterCustomerRequest;
import com.bank.MyBankApp.customer.dto.response.CustomerResponse;
import com.bank.MyBankApp.customer.dto.response.LoginResponse;
import com.bank.MyBankApp.customer.dto.response.RegisterCustomerResponse;
import com.bank.MyBankApp.exception.NotFoundException;
import com.bank.MyBankApp.customer.model.Gender;
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
    private CustomerService customerService;
    @Autowired
    private MyBankJwtTokenRepository jwtTokenRepository;

    private RegisterCustomerRequest registerCustomerRequest1;
    private RegisterCustomerRequest registerCustomerRequest2;
    private RegisterCustomerRequest registerCustomerRequest3;
    private RegisterCustomerRequest registerCustomerRequest4;

    private AddAddressRequest addressRequest1;
    private AddAddressRequest addressRequest2;
    private AddAddressRequest addressRequest3;
    private AddAddressRequest addressRequest4;


    @BeforeEach
    void setUp() {
        AddAppUserRequest addAppUserRequest1 = new AddAppUserRequest();
        addAppUserRequest1.setFirstName("Femz");
        addAppUserRequest1.setMiddleName("Remz");
        addAppUserRequest1.setLastName("Temz");
        addAppUserRequest1.setEmail("temx@email.com");
        addAppUserRequest1.setPhoneNumber("09078900458");
        addAppUserRequest1.setPassword("password1");

        registerCustomerRequest1 = new RegisterCustomerRequest();
        registerCustomerRequest1.setAddAppUserRequest(addAppUserRequest1);
        registerCustomerRequest1.setDateOfBirth("21/01/2002");
        registerCustomerRequest1.setGender(Gender.MALE);
        registerCustomerRequest1.setBvn("12345678901");
        registerCustomerRequest1.setNin("09876543210");

        AddAppUserRequest addAppUserRequest2 = new AddAppUserRequest();
        addAppUserRequest2.setFirstName("Rhoda");
        addAppUserRequest2.setMiddleName("Rolis");
        addAppUserRequest2.setLastName("Remi");
        addAppUserRequest2.setEmail("remi@email.com");
        addAppUserRequest2.setPhoneNumber("08078956458");
        addAppUserRequest2.setPassword("password2");

        registerCustomerRequest2 = new RegisterCustomerRequest();
        registerCustomerRequest2.setAddAppUserRequest(addAppUserRequest2);
        registerCustomerRequest2.setDateOfBirth("22/01/2002");
        registerCustomerRequest2.setGender(Gender.FEMALE);
        registerCustomerRequest2.setBvn("21345678956");
        registerCustomerRequest2.setNin("59876643211");

        AddAppUserRequest addAppUserRequest3 = new AddAppUserRequest();
        addAppUserRequest3.setFirstName("Salah");
        addAppUserRequest3.setMiddleName("Sola");
        addAppUserRequest3.setLastName("Saheed");
        addAppUserRequest3.setEmail("saheed@email.com");
        addAppUserRequest3.setPhoneNumber("08178900478");
        addAppUserRequest3.setPassword("password3");

        registerCustomerRequest3 = new RegisterCustomerRequest();
        registerCustomerRequest3.setAddAppUserRequest(addAppUserRequest3);
        registerCustomerRequest3.setDateOfBirth("23/01/2002");
        registerCustomerRequest3.setGender(Gender.MALE);
        registerCustomerRequest3.setBvn("32345078905");
        registerCustomerRequest3.setNin("29876643219");

        AddAppUserRequest addAppUserRequest4 = new AddAppUserRequest();
        addAppUserRequest4.setFirstName("Gift");
        addAppUserRequest4.setMiddleName("Gifted");
        addAppUserRequest4.setLastName("Glory");
        addAppUserRequest4.setEmail("glory@email.com");
        addAppUserRequest4.setPhoneNumber("08090900898");
        addAppUserRequest4.setPassword("password4");


        registerCustomerRequest4 = new RegisterCustomerRequest();
        registerCustomerRequest4.setAddAppUserRequest(addAppUserRequest4);
        registerCustomerRequest4.setDateOfBirth("24/01/2002");
        registerCustomerRequest4.setGender(Gender.FEMALE);
        registerCustomerRequest4.setBvn("62345378909");
        registerCustomerRequest4.setNin("19876903212");

        addressRequest1 = new AddAddressRequest();
        addressRequest1.setStreetNumber(1);
        addressRequest1.setStreetName("Herbert Macaulay way");
        addressRequest1.setTownName("Sabo");
        addressRequest1.setCityName("Yaba");
        addressRequest1.setState("Lagos");
        addressRequest1.setCountry("Nigeria");

        addressRequest2 = new AddAddressRequest();
        addressRequest2.setStreetNumber(2);
        addressRequest2.setStreetName("Herbert Macaulay way");
        addressRequest2.setTownName("Sabo");
        addressRequest2.setCityName("Yaba");
        addressRequest2.setState("Lagos");
        addressRequest2.setCountry("Nigeria");

        addressRequest3 = new AddAddressRequest();
        addressRequest3.setStreetNumber(3);
        addressRequest3.setStreetName("Herbert Macaulay way");
        addressRequest3.setTownName("Sabo");
        addressRequest3.setCityName("Yaba");
        addressRequest3.setState("Lagos");
        addressRequest3.setCountry("Nigeria");

        addressRequest4 = new AddAddressRequest();
        addressRequest4.setStreetNumber(4);
        addressRequest4.setStreetName("Herbert Macaulay way");
        addressRequest4.setTownName("Sabo");
        addressRequest4.setCityName("Yaba");
        addressRequest4.setState("Lagos");
        addressRequest4.setCountry("Nigeria");
    }

    @AfterEach
    void tearDown(){
        customerService.deleteAll();
        jwtTokenRepository.deleteAll();
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
    void loginTest(){
        RegisterCustomerResponse response = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(response.getCustomerId()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("Registration successful");

        String addAddressResponse = customerService.addCustomerAddress(addressRequest1, 1);
        assertThat(addAddressResponse).isEqualTo("Address added successfully");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("temx@email.com");
        loginRequest.setPassword("password1");
        LoginResponse loginResponse = customerService.login(loginRequest);
        assertThat(loginResponse.getMessage()).isEqualTo("Login successful");
        assertThat(loginResponse.getJwtResponse()).isNotNull();
    }

    @Test
    void getCustomerByIdTest(){
        RegisterCustomerResponse response = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(response.getCustomerId()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("Registration successful");

        String addAddressResponse = customerService.addCustomerAddress(addressRequest1, 1);
        assertThat(addAddressResponse).isEqualTo("Address added successfully");

        CustomerResponse customerResponse = customerService.getCustomerById(1);
        assertThat(customerResponse.getFirstName()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getFirstName());
        assertThat(customerResponse.getMiddleName()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getMiddleName());
        assertThat(customerResponse.getLastName()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getLastName());
        assertThat(customerResponse.getEmail()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getEmail());
        assertThat(customerResponse.getPhoneNumber()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getPhoneNumber());
        assertThat(customerResponse.getGender()).isEqualTo(registerCustomerRequest1.getGender());
        assertThat(customerResponse.getDateOfBirth()).isEqualTo(registerCustomerRequest1.getDateOfBirth());
        assertThat(customerResponse.getAddressResponse()).isNotNull();
    }

    @Test
    void getCustomerByEmailTest(){
        RegisterCustomerResponse response = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(response.getCustomerId()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("Registration successful");

        String addAddressResponse = customerService.addCustomerAddress(addressRequest1, 1);
        assertThat(addAddressResponse).isEqualTo("Address added successfully");

        CustomerResponse customerResponse = customerService.getCustomerByEmail("temx@email.com");
        assertThat(customerResponse.getFirstName()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getFirstName());
        assertThat(customerResponse.getMiddleName()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getMiddleName());
        assertThat(customerResponse.getLastName()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getLastName());
        assertThat(customerResponse.getEmail()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getEmail());
        assertThat(customerResponse.getPhoneNumber()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getPhoneNumber());
        assertThat(customerResponse.getGender()).isEqualTo(registerCustomerRequest1.getGender());
        assertThat(customerResponse.getDateOfBirth()).isEqualTo(registerCustomerRequest1.getDateOfBirth());
        assertThat(customerResponse.getAddressResponse()).isNotNull();
    }

  @Test
    void getCustomerByPhoneNumberTest(){
        RegisterCustomerResponse response = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(response.getCustomerId()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("Registration successful");

        String addAddressResponse = customerService.addCustomerAddress(addressRequest1, 1);
        assertThat(addAddressResponse).isEqualTo("Address added successfully");

        CustomerResponse customerResponse = customerService.getCustomerByPhoneNumber("09078900458");
        assertThat(customerResponse.getFirstName()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getFirstName());
        assertThat(customerResponse.getMiddleName()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getMiddleName());
        assertThat(customerResponse.getLastName()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getLastName());
        assertThat(customerResponse.getEmail()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getEmail());
        assertThat(customerResponse.getPhoneNumber()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getPhoneNumber());
        assertThat(customerResponse.getGender()).isEqualTo(registerCustomerRequest1.getGender());
        assertThat(customerResponse.getDateOfBirth()).isEqualTo(registerCustomerRequest1.getDateOfBirth());
        assertThat(customerResponse.getAddressResponse()).isNotNull();
    }

  @Test
    void getCustomerByNinTest(){
        RegisterCustomerResponse response = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(response.getCustomerId()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("Registration successful");

        String addAddressResponse = customerService.addCustomerAddress(addressRequest1, 1);
        assertThat(addAddressResponse).isEqualTo("Address added successfully");

        CustomerResponse customerResponse = customerService.getCustomerByNin("09876543210");
        assertThat(customerResponse.getFirstName()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getFirstName());
        assertThat(customerResponse.getMiddleName()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getMiddleName());
        assertThat(customerResponse.getLastName()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getLastName());
        assertThat(customerResponse.getEmail()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getEmail());
        assertThat(customerResponse.getPhoneNumber()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getPhoneNumber());
        assertThat(customerResponse.getGender()).isEqualTo(registerCustomerRequest1.getGender());
        assertThat(customerResponse.getDateOfBirth()).isEqualTo(registerCustomerRequest1.getDateOfBirth());
        assertThat(customerResponse.getAddressResponse()).isNotNull();
    }

  @Test
    void getCustomerByBvnTest(){
        RegisterCustomerResponse response = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(response.getCustomerId()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("Registration successful");

        String addAddressResponse = customerService.addCustomerAddress(addressRequest1, 1);
        assertThat(addAddressResponse).isEqualTo("Address added successfully");

        CustomerResponse customerResponse = customerService.getCustomerByBvn("12345678901");
        assertThat(customerResponse.getFirstName()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getFirstName());
        assertThat(customerResponse.getMiddleName()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getMiddleName());
        assertThat(customerResponse.getLastName()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getLastName());
        assertThat(customerResponse.getEmail()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getEmail());
        assertThat(customerResponse.getPhoneNumber()).isEqualTo(registerCustomerRequest1.getAddAppUserRequest().getPhoneNumber());
        assertThat(customerResponse.getGender()).isEqualTo(registerCustomerRequest1.getGender());
        assertThat(customerResponse.getDateOfBirth()).isEqualTo(registerCustomerRequest1.getDateOfBirth());
        assertThat(customerResponse.getAddressResponse()).isNotNull();
    }

  @Test
    void getCustomerByPhoneNumberThrowsExceptionTest(){
        RegisterCustomerResponse response = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(response.getCustomerId()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("Registration successful");

        String addAddressResponse = customerService.addCustomerAddress(addressRequest1, 1);
        assertThat(addAddressResponse).isEqualTo("Address added successfully");

        NotFoundException exception = assertThrows(NotFoundException.class,
                ()-> customerService.getCustomerByPhoneNumber("09078900459"));
        assertThat(exception.getMessage()).isEqualTo("Customer with this phone number not found");
    }

    @Test
    void getAllCustomerTest(){
        RegisterCustomerResponse response = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(response.getCustomerId()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("Registration successful");
        String addAddressResponse = customerService.addCustomerAddress(addressRequest1, 1);
        assertThat(addAddressResponse).isEqualTo("Address added successfully");
        assertThat(customerService.count()).isEqualTo(1);

        RegisterCustomerResponse response2 = customerService.registerCustomer(registerCustomerRequest2);
        assertThat(response2.getCustomerId()).isEqualTo(2);
        assertThat(response2.getMessage()).isEqualTo("Registration successful");
        String addAddressResponse2 = customerService.addCustomerAddress(addressRequest2, 2);
        assertThat(addAddressResponse2).isEqualTo("Address added successfully");
        assertThat(customerService.count()).isEqualTo(2);

        RegisterCustomerResponse response3 = customerService.registerCustomer(registerCustomerRequest3);
        assertThat(response3.getCustomerId()).isEqualTo(3);
        assertThat(response3.getMessage()).isEqualTo("Registration successful");
        String addAddressResponse3 = customerService.addCustomerAddress(addressRequest3, 3);
        assertThat(addAddressResponse3).isEqualTo("Address added successfully");
        assertThat(customerService.count()).isEqualTo(3);

        RegisterCustomerResponse response4 = customerService.registerCustomer(registerCustomerRequest4);
        assertThat(response4.getCustomerId()).isEqualTo(4);
        assertThat(response4.getMessage()).isEqualTo("Registration successful");
        String addAddressResponse4 = customerService.addCustomerAddress(addressRequest4, 4);
        assertThat(addAddressResponse4).isEqualTo("Address added successfully");
        assertThat(customerService.count()).isEqualTo(4);

        assertThat(customerService.count()).isEqualTo(4);
    }
    @Test
    void deleteByCustomerIdTest(){
        RegisterCustomerResponse response = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(response.getCustomerId()).isEqualTo(1);
        assertThat(response.getMessage()).isEqualTo("Registration successful");

        String addAddressResponse = customerService.addCustomerAddress(addressRequest1, 1);
        assertThat(addAddressResponse).isEqualTo("Address added successfully");

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

        RegisterCustomerResponse response2 = customerService.registerCustomer(registerCustomerRequest2);
        assertThat(response2.getCustomerId()).isEqualTo(2);
        assertThat(response2.getMessage()).isEqualTo("Registration successful");
        String addAddressResponse2 = customerService.addCustomerAddress(addressRequest2, 2);
        assertThat(addAddressResponse2).isEqualTo("Address added successfully");

        RegisterCustomerResponse response3 = customerService.registerCustomer(registerCustomerRequest3);
        assertThat(response3.getCustomerId()).isEqualTo(3);
        assertThat(response3.getMessage()).isEqualTo("Registration successful");
        String addAddressResponse3 = customerService.addCustomerAddress(addressRequest3, 3);
        assertThat(addAddressResponse3).isEqualTo("Address added successfully");

        RegisterCustomerResponse response4 = customerService.registerCustomer(registerCustomerRequest4);
        assertThat(response4.getCustomerId()).isEqualTo(4);
        assertThat(response4.getMessage()).isEqualTo("Registration successful");
        String addAddressResponse4 = customerService.addCustomerAddress(addressRequest4, 4);
        assertThat(addAddressResponse4).isEqualTo("Address added successfully");
        assertThat(customerService.count()).isEqualTo(4);

        customerService.deleteAll();
        assertThat(customerService.count()).isEqualTo(0);
    }
}