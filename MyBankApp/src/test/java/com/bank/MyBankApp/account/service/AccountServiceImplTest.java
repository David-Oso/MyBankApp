package com.bank.MyBankApp.account.service;

import com.bank.MyBankApp.account.dto.response.CreateAccountResponse;
import com.bank.MyBankApp.account.dto.response.TransactionResponse;
import com.bank.MyBankApp.account.model.Account;
import com.bank.MyBankApp.account.model.AccountType;
import com.bank.MyBankApp.account.dto.request.CreateAccountRequest;
import com.bank.MyBankApp.account.dto.request.DepositRequest;
import com.bank.MyBankApp.account.dto.request.TransferRequest;
import com.bank.MyBankApp.account.dto.request.WithdrawRequest;
import com.bank.MyBankApp.customer.dto.request.AddAppUserRequest;
import com.bank.MyBankApp.customer.dto.request.RegisterCustomerRequest;
import com.bank.MyBankApp.customer.dto.response.RegisterCustomerResponse;
import com.bank.MyBankApp.customer.model.Gender;
import com.bank.MyBankApp.customer.service.CustomerService;
import com.bank.MyBankApp.transaction.model.TransactionType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class AccountServiceImplTest {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerService customerService;
    private RegisterCustomerRequest registerCustomerRequest1;
    private RegisterCustomerRequest registerCustomerRequest2;
    private CreateAccountRequest createAccountRequest1;
    private CreateAccountRequest createAccountRequest2;

    @BeforeEach
    void setUp() {
        AddAppUserRequest addAppUserRequest1 = new AddAppUserRequest();
        addAppUserRequest1.setEmail("osodavid001@gmail.com");
//        addAppUserRequest1.setEmail("temx@gmail.com");
        addAppUserRequest1.setPassword("password1");
        addAppUserRequest1.setPhoneNumber("09078900458");
        addAppUserRequest1.setLastName("Temz");
        addAppUserRequest1.setFirstName("Femz");
        addAppUserRequest1.setMiddleName("Remz");

        registerCustomerRequest1 = new RegisterCustomerRequest();
        registerCustomerRequest1.setAddAppUserRequest(addAppUserRequest1);
        registerCustomerRequest1.setDateOfBirth("21/01/2002");
        registerCustomerRequest1.setGender(Gender.MALE);
        registerCustomerRequest1.setBvn("12345678901");
        registerCustomerRequest1.setNin("09876543210");

        AddAppUserRequest addAppUserRequest2 = new AddAppUserRequest();
        addAppUserRequest2.setEmail("osodavid272@gmail.com");
//        addAppUserRequest2.setEmail("remi@email.com");
        addAppUserRequest2.setPassword("password2");
        addAppUserRequest2.setPhoneNumber("08078956458");
        addAppUserRequest2.setLastName("Remi");
        addAppUserRequest2.setFirstName("Rhoda");
        addAppUserRequest2.setMiddleName("Rolis");

        registerCustomerRequest2 = new RegisterCustomerRequest();
        registerCustomerRequest2.setAddAppUserRequest(addAppUserRequest2);
        registerCustomerRequest2.setDateOfBirth("22/01/2002");
        registerCustomerRequest2.setGender(Gender.FEMALE);
        registerCustomerRequest2.setBvn("21345678956");
        registerCustomerRequest2.setNin("59876643211");

        createAccountRequest1 = new CreateAccountRequest();
        createAccountRequest1.setAccountType(AccountType.SAVINGS);
        createAccountRequest1.setPin("1234");

        createAccountRequest2 = new CreateAccountRequest();
        createAccountRequest2.setAccountType(AccountType.CURRENT);
        createAccountRequest2.setPin("2345");
    }

    @AfterEach
    void tearDown() {
        customerService.deleteAll();
        accountService.deleteAllAccounts();
        entityManager.createNativeQuery("ALTER TABLE my_bank_app_testdb.customers AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE my_bank_app_testdb.accounts AUTO_INCREMENT = 1").executeUpdate();
    }

    @Test
    void createNewAccountTest() {
        RegisterCustomerResponse registerCustomerResponse = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(registerCustomerResponse.getCustomerId()).isEqualTo(1);
        assertThat(registerCustomerResponse.getMessage()).isEqualTo("Registration successful");

        createAccountRequest1.setCustomerId(registerCustomerResponse.getCustomerId());
        CreateAccountResponse createAccountResponse = accountService.createNewAccount(createAccountRequest1);
        assertThat(createAccountResponse.getAccountName()).isEqualTo("%s %s"
                .formatted(registerCustomerRequest1.getAddAppUserRequest().getFirstName(), registerCustomerRequest1.getAddAppUserRequest().getLastName()));
        assertThat(createAccountResponse.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(createAccountResponse.getAccountNumber()).isNotNull();
    }

    @Test
    void depositMoneyTest() {
        RegisterCustomerResponse registerCustomerResponse = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(registerCustomerResponse.getCustomerId()).isEqualTo(1);
        assertThat(registerCustomerResponse.getMessage()).isEqualTo("Registration successful");

        createAccountRequest1.setCustomerId(registerCustomerResponse.getCustomerId());
        CreateAccountResponse createAccountResponse = accountService.createNewAccount(createAccountRequest1);
        assertThat(createAccountResponse.getAccountName()).isEqualTo("%s %s"
                .formatted(registerCustomerRequest1.getAddAppUserRequest().getFirstName(), registerCustomerRequest1.getAddAppUserRequest().getLastName()));
        assertThat(createAccountResponse.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(createAccountResponse.getAccountNumber()).isNotNull();

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountId(1);
        depositRequest.setAmount(BigDecimal.valueOf(20000.00));

        String depositResponse = accountService.depositMoney(depositRequest);
        assertThat(depositResponse).isEqualTo("Transaction successful");

        assertThat(accountService.getBalance(1, createAccountRequest1.getPin()))
                .isEqualTo("₦20,000.00");
    }

    @Test
    void withdrawMoneyTest() {
        RegisterCustomerResponse registerCustomerResponse = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(registerCustomerResponse.getCustomerId()).isEqualTo(1);
        assertThat(registerCustomerResponse.getMessage()).isEqualTo("Registration successful");

        createAccountRequest1.setCustomerId(registerCustomerResponse.getCustomerId());
        CreateAccountResponse createAccountResponse = accountService.createNewAccount(createAccountRequest1);
        assertThat(createAccountResponse.getAccountName()).isEqualTo("%s %s"
                .formatted(registerCustomerRequest1.getAddAppUserRequest().getFirstName(), registerCustomerRequest1.getAddAppUserRequest().getLastName()));
        assertThat(createAccountResponse.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(createAccountResponse.getAccountNumber()).isNotNull();

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountId(1);
        depositRequest.setAmount(BigDecimal.valueOf(20000.00));

        String depositResponse = accountService.depositMoney(depositRequest);
        assertThat(depositResponse).isEqualTo("Transaction successful");

        assertThat(accountService.getBalance(1, createAccountRequest1.getPin()))
                .isEqualTo("₦20,000.00");
        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountId(1);
        withdrawRequest.setAmount(BigDecimal.valueOf(10000.00));
        withdrawRequest.setPin(createAccountRequest1.getPin());

        String withdrawResponse = accountService.withdrawMoney(withdrawRequest);
        assertThat(withdrawResponse).isEqualTo("Transaction successful");

        assertThat(accountService.getBalance(1, createAccountRequest1.getPin()))
                .isEqualTo("₦10,000.00");

    }

    @Test
    void transferMoneyTest(){
        RegisterCustomerResponse registerResponse1 = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(registerResponse1.getCustomerId()).isEqualTo(1);
        assertThat(registerResponse1.getMessage()).isEqualTo("Registration successful");

        createAccountRequest1.setCustomerId(registerResponse1.getCustomerId());
        CreateAccountResponse createAccountResponse1 = accountService.createNewAccount(createAccountRequest1);
        assertThat(createAccountResponse1.getAccountName()).isEqualTo("%s %s".formatted(
                registerCustomerRequest1.getAddAppUserRequest().getFirstName(), registerCustomerRequest1.getAddAppUserRequest().getLastName()));
        assertThat(createAccountResponse1.getAccountType()).isEqualTo(AccountType.SAVINGS);

//      CREATE RECIPIENT ACCOUNT

        RegisterCustomerResponse registerResponse2 = customerService.registerCustomer(registerCustomerRequest2);
        assertThat(registerResponse2.getCustomerId()).isEqualTo(2);
        assertThat(registerResponse2.getMessage()).isEqualTo("Registration successful");

        createAccountRequest2.setCustomerId(registerResponse2.getCustomerId());
        CreateAccountResponse createAccountResponse2 = accountService.createNewAccount(createAccountRequest2);
        assertThat(createAccountResponse2.getAccountName()).isEqualTo("%s %s".formatted(
                registerCustomerRequest2.getAddAppUserRequest().getFirstName(), registerCustomerRequest2.getAddAppUserRequest().getLastName()));
        assertThat(createAccountResponse2.getAccountType()).isEqualTo(AccountType.CURRENT);
        assertThat(createAccountResponse2.getAccountNumber()).isNotNull();

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountId(1);
        depositRequest.setAmount(BigDecimal.valueOf(20000.00));

        String depositResponse = accountService.depositMoney(depositRequest);
        assertThat(depositResponse).isEqualTo("Transaction successful");

        assertThat(accountService.getBalance(1, createAccountRequest1.getPin()))
                .isEqualTo("₦20,000.00");

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setAccountId(1);
        transferRequest.setRecipientAccountNumber(createAccountResponse2.getAccountNumber());
        transferRequest.setAmount(BigDecimal.valueOf(10000.00));
        transferRequest.setPin(createAccountRequest1.getPin());

        String transferResponse = accountService.transferMoney(transferRequest);
        assertThat(transferResponse).isEqualTo("Transaction successful");

       assertThat(accountService.getBalance(1, createAccountRequest1.getPin()))
                .isEqualTo("₦10,000.00");

       assertThat(accountService.getBalance(2, createAccountRequest2.getPin()))
                .isEqualTo("₦10,000.00");

    }

    @Test
    void getBalanceTest() {
        RegisterCustomerResponse registerCustomerResponse = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(registerCustomerResponse.getCustomerId()).isEqualTo(1);
        assertThat(registerCustomerResponse.getMessage()).isEqualTo("Registration successful");

        createAccountRequest1.setCustomerId(registerCustomerResponse.getCustomerId());
        CreateAccountResponse createAccountResponse = accountService.createNewAccount(createAccountRequest1);
        assertThat(createAccountResponse.getAccountName()).isEqualTo("%s %s"
                .formatted(registerCustomerRequest1.getAddAppUserRequest().getFirstName(), registerCustomerRequest1.getAddAppUserRequest().getLastName()));
        assertThat(createAccountResponse.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(createAccountResponse.getAccountNumber()).isNotNull();

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountId(1);
        depositRequest.setAmount(BigDecimal.valueOf(20000.00));

        String depositResponse = accountService.depositMoney(depositRequest);
        assertThat(depositResponse).isEqualTo("Transaction successful");

        assertThat(accountService.getBalance(1, createAccountRequest1.getPin()))
                .isEqualTo("₦20,000.00");
    }

    @Test
    void getAccountTransactions() {
        RegisterCustomerResponse registerCustomerResponse = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(registerCustomerResponse.getCustomerId()).isEqualTo(1);
        assertThat(registerCustomerResponse.getMessage()).isEqualTo("Registration successful");

        createAccountRequest1.setCustomerId(registerCustomerResponse.getCustomerId());
        CreateAccountResponse createAccountResponse = accountService.createNewAccount(createAccountRequest1);
        assertThat(createAccountResponse.getAccountName()).isEqualTo("%s %s"
                .formatted(registerCustomerRequest1.getAddAppUserRequest().getFirstName(), registerCustomerRequest1.getAddAppUserRequest().getLastName()));
        assertThat(createAccountResponse.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(createAccountResponse.getAccountNumber()).isNotNull();

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountId(1);
        depositRequest.setAmount(BigDecimal.valueOf(20000.00));

        String depositResponse = accountService.depositMoney(depositRequest);
        assertThat(depositResponse).isEqualTo("Transaction successful");

        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountId(1);
        withdrawRequest.setAmount(BigDecimal.valueOf(10000.00));
        withdrawRequest.setPin(createAccountRequest1.getPin());

        String withdrawResponse = accountService.withdrawMoney(withdrawRequest);
        assertThat(withdrawResponse).isEqualTo("Transaction successful");

        assertThat(accountService.getAccountTransactions(registerCustomerResponse.getCustomerId())
                .size()).isEqualTo(2);
    }

    @Test
    void deleteAccountByAccountAndCustomerIdTest(){
        RegisterCustomerResponse registerCustomerResponse = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(registerCustomerResponse.getCustomerId()).isEqualTo(1);
        assertThat(registerCustomerResponse.getMessage()).isEqualTo("Registration successful");

        createAccountRequest1.setCustomerId(registerCustomerResponse.getCustomerId());
        CreateAccountResponse createAccountResponse = accountService.createNewAccount(createAccountRequest1);
        assertThat(createAccountResponse.getAccountName()).isEqualTo("%s %s"
                .formatted(registerCustomerRequest1.getAddAppUserRequest().getFirstName(), registerCustomerRequest1.getAddAppUserRequest().getLastName()));
        assertThat(createAccountResponse.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(createAccountResponse.getAccountNumber()).isNotNull();

        createAccountRequest2.setCustomerId(registerCustomerResponse.getCustomerId());
        CreateAccountResponse createAccountResponse2 = accountService.createNewAccount(createAccountRequest2);
        assertThat(createAccountResponse2.getAccountName()).isEqualTo("%s %s"
                .formatted(registerCustomerRequest1.getAddAppUserRequest().getFirstName(), registerCustomerRequest1.getAddAppUserRequest().getLastName()));
        assertThat(createAccountResponse2.getAccountType()).isEqualTo(AccountType.CURRENT);
        assertThat(createAccountResponse2.getAccountNumber()).isNotNull();

        assertThat(accountService.numberOfCustomerAccounts(registerCustomerResponse.getCustomerId()))
                .isEqualTo(2);

        accountService.deleteAccountByAccountAndCustomerId(2, registerCustomerResponse.getCustomerId());

        assertThat(accountService.numberOfCustomerAccounts(registerCustomerResponse.getCustomerId()))
                .isEqualTo(1);
    }

    @Test
    void deleteAllAccountCustomerIdTest(){
        RegisterCustomerResponse registerCustomerResponse = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(registerCustomerResponse.getCustomerId()).isEqualTo(1);
        assertThat(registerCustomerResponse.getMessage()).isEqualTo("Registration successful");

        createAccountRequest1.setCustomerId(registerCustomerResponse.getCustomerId());
        CreateAccountResponse createAccountResponse = accountService.createNewAccount(createAccountRequest1);
        assertThat(createAccountResponse.getAccountName()).isEqualTo("%s %s"
                .formatted(registerCustomerRequest1.getAddAppUserRequest().getFirstName(), registerCustomerRequest1.getAddAppUserRequest().getLastName()));
        assertThat(createAccountResponse.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(createAccountResponse.getAccountNumber()).isNotNull();

        createAccountRequest2.setCustomerId(registerCustomerResponse.getCustomerId());
        CreateAccountResponse createAccountResponse2 = accountService.createNewAccount(createAccountRequest2);
        assertThat(createAccountResponse2.getAccountName()).isEqualTo("%s %s"
                .formatted(registerCustomerRequest1.getAddAppUserRequest().getFirstName(), registerCustomerRequest1.getAddAppUserRequest().getLastName()));
        assertThat(createAccountResponse2.getAccountType()).isEqualTo(AccountType.CURRENT);
        assertThat(createAccountResponse2.getAccountNumber()).isNotNull();

        assertThat(accountService.numberOfCustomerAccounts(registerCustomerResponse.getCustomerId()))
                .isEqualTo(2);

        accountService.deleteAllAccountByCustomerId(registerCustomerResponse.getCustomerId());

        assertThat(accountService.numberOfCustomerAccounts(registerCustomerResponse.getCustomerId()))
                .isEqualTo(0);
    }

    @Test
    void deleteAllAccountsTest(){
        RegisterCustomerResponse registerCustomerResponse = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(registerCustomerResponse.getCustomerId()).isEqualTo(1);
        assertThat(registerCustomerResponse.getMessage()).isEqualTo("Registration successful");

        createAccountRequest1.setCustomerId(registerCustomerResponse.getCustomerId());
        CreateAccountResponse createAccountResponse = accountService.createNewAccount(createAccountRequest1);
        assertThat(createAccountResponse.getAccountName()).isEqualTo("%s %s"
                .formatted(registerCustomerRequest1.getAddAppUserRequest().getFirstName(), registerCustomerRequest1.getAddAppUserRequest().getLastName()));
        assertThat(createAccountResponse.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(createAccountResponse.getAccountNumber()).isNotNull();

        RegisterCustomerResponse registerCustomerResponse2 = customerService.registerCustomer(registerCustomerRequest2);
        assertThat(registerCustomerResponse2.getCustomerId()).isEqualTo(2);
        assertThat(registerCustomerResponse2.getMessage()).isEqualTo("Registration successful");

        createAccountRequest2.setCustomerId(registerCustomerResponse2.getCustomerId());
        CreateAccountResponse createAccountResponse2 = accountService.createNewAccount(createAccountRequest2);
        assertThat(createAccountResponse2.getAccountName()).isEqualTo("%s %s"
                .formatted(registerCustomerRequest2.getAddAppUserRequest().getFirstName(), registerCustomerRequest2.getAddAppUserRequest().getLastName()));
        assertThat(createAccountResponse2.getAccountType()).isEqualTo(AccountType.CURRENT);
        assertThat(createAccountResponse2.getAccountNumber()).isNotNull();

        assertThat(accountService.numberOfAccounts()).isEqualTo(2);

        accountService.deleteAllAccounts();

        assertThat(accountService.numberOfAccounts()).isEqualTo(0);
    }
}