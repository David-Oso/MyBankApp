package com.bank.MyBankApp.account.service;

import com.bank.MyBankApp.account.dto.response.CreateAccountResponse;
import com.bank.MyBankApp.account.dto.response.TransactionResponse;
import com.bank.MyBankApp.account.model.Account;
import com.bank.MyBankApp.account.model.AccountType;
import com.bank.MyBankApp.account.dto.request.CreateAccountRequest;
import com.bank.MyBankApp.account.dto.request.DepositRequest;
import com.bank.MyBankApp.account.dto.request.TransferRequest;
import com.bank.MyBankApp.account.dto.request.WithdrawRequest;
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
        registerCustomerRequest1 = new RegisterCustomerRequest();
        registerCustomerRequest1.setDateOfBirth("21/01/2002");
        registerCustomerRequest1.setEmail("temx@email.com");
        registerCustomerRequest1.setPassword("password1");
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
        registerCustomerRequest2.setPassword("password2");
        registerCustomerRequest2.setPhoneNumber("08078956458");
        registerCustomerRequest2.setGender(Gender.FEMALE);
        registerCustomerRequest2.setLastName("Remi");
        registerCustomerRequest2.setFirstName("Rhoda");
        registerCustomerRequest2.setMiddleName("Rolis");
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
                .formatted(registerCustomerRequest1.getFirstName(), registerCustomerRequest1.getLastName()));
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
                .formatted(registerCustomerRequest1.getFirstName(), registerCustomerRequest1.getLastName()));
        assertThat(createAccountResponse.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(createAccountResponse.getAccountNumber()).isNotNull();

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountId(1);
        depositRequest.setAmount(BigDecimal.valueOf(20000.00));

        String depositResponse = accountService.depositMoney(depositRequest);
        assertThat(depositResponse).isEqualTo("Transaction successful");

        assertThat(accountService.getBalance(1, createAccountRequest1.getPin()))
                .isEqualTo(BigDecimal.valueOf(20000.00));
    }

    @Test
    void withdrawMoneyTest() {
        RegisterCustomerResponse registerCustomerResponse = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(registerCustomerResponse.getCustomerId()).isEqualTo(1);
        assertThat(registerCustomerResponse.getMessage()).isEqualTo("Registration successful");

        createAccountRequest1.setCustomerId(registerCustomerResponse.getCustomerId());
        CreateAccountResponse createAccountResponse = accountService.createNewAccount(createAccountRequest1);
        assertThat(createAccountResponse.getAccountName()).isEqualTo("%s %s"
                .formatted(registerCustomerRequest1.getFirstName(), registerCustomerRequest1.getLastName()));
        assertThat(createAccountResponse.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(createAccountResponse.getAccountNumber()).isNotNull();

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountId(1);
        depositRequest.setAmount(BigDecimal.valueOf(20000.00));

        String depositResponse = accountService.depositMoney(depositRequest);
        assertThat(depositResponse).isEqualTo("Transaction successful");

        assertThat(accountService.getBalance(1, createAccountRequest1.getPin()))
                .isEqualTo(BigDecimal.valueOf(20000.00));
        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountId(1);
        withdrawRequest.setAmount(BigDecimal.valueOf(10000.00));
        withdrawRequest.setPin(createAccountRequest1.getPin());

        String withdrawResponse = accountService.withdrawMoney(withdrawRequest);
        assertThat(withdrawResponse).isEqualTo("Transaction successful");

        assertThat(accountService.getBalance(1, createAccountRequest1.getPin()))
                .isEqualTo(BigDecimal.valueOf(10000.00));

    }

    @Test
    void transferMoneyTest() {
        RegisterCustomerResponse registerCustomerResponse = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(registerCustomerResponse.getCustomerId()).isEqualTo(1);
        assertThat(registerCustomerResponse.getMessage()).isEqualTo("Registration successful");

        createAccountRequest1.setCustomerId(registerCustomerResponse.getCustomerId());
        CreateAccountResponse createAccountResponse = accountService.createNewAccount(createAccountRequest1);
        assertThat(createAccountResponse.getAccountName()).isEqualTo("%s %s"
                .formatted(registerCustomerRequest1.getFirstName(), registerCustomerRequest1.getLastName()));
        assertThat(createAccountResponse.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(createAccountResponse.getAccountNumber()).isNotNull();

         RegisterCustomerResponse registerCustomerResponse2 = customerService.registerCustomer(registerCustomerRequest2);
        assertThat(registerCustomerResponse2.getCustomerId()).isEqualTo(2);
        assertThat(registerCustomerResponse2.getMessage()).isEqualTo("Registration successful");

        createAccountRequest2.setCustomerId(registerCustomerResponse2.getCustomerId());
        CreateAccountResponse createAccountResponse2 = accountService.createNewAccount(createAccountRequest2);
        assertThat(createAccountResponse2.getAccountName()).isEqualTo("%s %s"
                .formatted(registerCustomerRequest2.getFirstName(), registerCustomerRequest2.getLastName()));
        assertThat(createAccountResponse2.getAccountType()).isEqualTo(AccountType.CURRENT);
        assertThat(createAccountResponse2.getAccountNumber()).isNotNull();

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountId(1);
        depositRequest.setAmount(BigDecimal.valueOf(20000.00));

        String depositResponse = accountService.depositMoney(depositRequest);
        assertThat(depositResponse).isEqualTo("Transaction successful");

        assertThat(accountService.getBalance(1, createAccountRequest1.getPin()))
                .isEqualTo(BigDecimal.valueOf(20000.00));

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setAccountId(1);
        transferRequest.setRecipientAccountNumber(createAccountResponse2.getAccountNumber());
        transferRequest.setAmount(BigDecimal.valueOf(10000.00));
        transferRequest.setPin(createAccountRequest1.getPin());

        String transferResponse = accountService.transferMoney(transferRequest);
        assertThat(transferResponse).isEqualTo("Transaction successful");

        assertThat(accountService.getBalance(1, createAccountRequest1.getPin()))
                .isEqualTo(BigDecimal.valueOf(10000.00));

        assertThat(accountService.getBalance(2, createAccountRequest2.getPin()))
                .isEqualTo(BigDecimal.valueOf(10000.00));

//        String transferResponse = accountService.transferMoney(transferRequest);
//        assertThat(transferResponse.getCurrentBalance()).isEqualTo("₦10000.0");
//
//        assertThat(accountService.getBalance(account2.getId(),
//                createAccountRequest2.getPin())).isEqualTo(BigDecimal.valueOf(10000.00));
    }

    @Test
    void getBalanceTest() {
        RegisterCustomerResponse registerCustomerResponse = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(registerCustomerResponse.getCustomerId()).isEqualTo(1);
        assertThat(registerCustomerResponse.getMessage()).isEqualTo("Registration successful");

        createAccountRequest1.setCustomerId(registerCustomerResponse.getCustomerId());
        CreateAccountResponse createAccountResponse = accountService.createNewAccount(createAccountRequest1);
        assertThat(createAccountResponse.getAccountName()).isEqualTo("%s %s"
                .formatted(registerCustomerRequest1.getFirstName(), registerCustomerRequest1.getLastName()));
        assertThat(createAccountResponse.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(createAccountResponse.getAccountNumber()).isNotNull();

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountId(1);
        depositRequest.setAmount(BigDecimal.valueOf(20000.00));

        String depositResponse = accountService.depositMoney(depositRequest);
        assertThat(depositResponse).isEqualTo("Transaction successful");

        assertThat(accountService.getBalance(1, createAccountRequest1.getPin()))
                .isEqualTo(BigDecimal.valueOf(20000.00));
    }

    @Test
    void getTransactionByAccountAndTransactionIdTest(){
        RegisterCustomerResponse registerCustomerResponse = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(registerCustomerResponse.getCustomerId()).isEqualTo(1);
        assertThat(registerCustomerResponse.getMessage()).isEqualTo("Registration successful");

        createAccountRequest1.setCustomerId(registerCustomerResponse.getCustomerId());
        CreateAccountResponse createAccountResponse = accountService.createNewAccount(createAccountRequest1);
        assertThat(createAccountResponse.getAccountName()).isEqualTo("%s %s"
                .formatted(registerCustomerRequest1.getFirstName(), registerCustomerRequest1.getLastName()));
        assertThat(createAccountResponse.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(createAccountResponse.getAccountNumber()).isNotNull();

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountId(1);
        depositRequest.setAmount(BigDecimal.valueOf(20000.00));

        String depositResponse = accountService.depositMoney(depositRequest);
        assertThat(depositResponse).isEqualTo("Transaction successful");

        TransactionResponse transactionResponse = accountService.getTransactionById(1, 1);
        assertThat(transactionResponse.getTransactionAmount()).isEqualTo("₦%s".formatted(2000.00));
        assertThat(transactionResponse.getTransactionType()).isEqualTo(TransactionType.CREDIT);
    }



    @Test
    void deleteAccountByAccountAndCustomerIdTest(){
        RegisterCustomerResponse registerCustomerResponse = customerService.registerCustomer(registerCustomerRequest1);
        assertThat(registerCustomerResponse.getCustomerId()).isEqualTo(1);
        assertThat(registerCustomerResponse.getMessage()).isEqualTo("Registration successful");

        createAccountRequest1.setCustomerId(registerCustomerResponse.getCustomerId());
        CreateAccountResponse createAccountResponse = accountService.createNewAccount(createAccountRequest1);
        assertThat(createAccountResponse.getAccountName()).isEqualTo("%s %s"
                .formatted(registerCustomerRequest1.getFirstName(), registerCustomerRequest1.getLastName()));
        assertThat(createAccountResponse.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(createAccountResponse.getAccountNumber()).isNotNull();

        createAccountRequest2.setCustomerId(registerCustomerResponse.getCustomerId());
        CreateAccountResponse createAccountResponse2 = accountService.createNewAccount(createAccountRequest2);
        assertThat(createAccountResponse2.getAccountName()).isEqualTo("%s %s"
                .formatted(registerCustomerRequest1.getFirstName(), registerCustomerRequest1.getLastName()));
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
                .formatted(registerCustomerRequest1.getFirstName(), registerCustomerRequest1.getLastName()));
        assertThat(createAccountResponse.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(createAccountResponse.getAccountNumber()).isNotNull();

        createAccountRequest2.setCustomerId(registerCustomerResponse.getCustomerId());
        CreateAccountResponse createAccountResponse2 = accountService.createNewAccount(createAccountRequest2);
        assertThat(createAccountResponse2.getAccountName()).isEqualTo("%s %s"
                .formatted(registerCustomerRequest1.getFirstName(), registerCustomerRequest1.getLastName()));
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
                .formatted(registerCustomerRequest1.getFirstName(), registerCustomerRequest1.getLastName()));
        assertThat(createAccountResponse.getAccountType()).isEqualTo(AccountType.SAVINGS);
        assertThat(createAccountResponse.getAccountNumber()).isNotNull();

        RegisterCustomerResponse registerCustomerResponse2 = customerService.registerCustomer(registerCustomerRequest2);
        assertThat(registerCustomerResponse2.getCustomerId()).isEqualTo(2);
        assertThat(registerCustomerResponse2.getMessage()).isEqualTo("Registration successful");

        createAccountRequest2.setCustomerId(registerCustomerResponse2.getCustomerId());
        CreateAccountResponse createAccountResponse2 = accountService.createNewAccount(createAccountRequest2);
        assertThat(createAccountResponse2.getAccountName()).isEqualTo("%s %s"
                .formatted(registerCustomerRequest2.getFirstName(), registerCustomerRequest2.getLastName()));
        assertThat(createAccountResponse2.getAccountType()).isEqualTo(AccountType.CURRENT);
        assertThat(createAccountResponse2.getAccountNumber()).isNotNull();

        assertThat(accountService.numberOfAccounts()).isEqualTo(2);

        accountService.deleteAllAccounts();

        assertThat(accountService.numberOfAccounts()).isEqualTo(0);
    }
}