package com.bank.MyBankApp.account.service;

import com.bank.MyBankApp.account.model.Account;
import com.bank.MyBankApp.account.model.AccountType;
import com.bank.MyBankApp.account.request.CreateAccountRequest;
import com.bank.MyBankApp.account.request.DepositRequest;
import com.bank.MyBankApp.account.request.TransferRequest;
import com.bank.MyBankApp.account.request.WithdrawRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class AccountServiceImplTest {
    @Autowired
    private AccountService accountService;
    private CreateAccountRequest createAccountRequest1;
    private CreateAccountRequest createAccountRequest2;

    @BeforeEach
    void setUp() {
        createAccountRequest1 = new CreateAccountRequest();
        createAccountRequest1.setAccountName("Sammy Shola");
        createAccountRequest1.setAccountType(AccountType.SAVINGS);
        createAccountRequest1.setPin("1234");

        createAccountRequest2 = new CreateAccountRequest();
        createAccountRequest2.setAccountName("Mummy Fave");
        createAccountRequest2.setAccountType(AccountType.SAVINGS);
        createAccountRequest2.setPin("2345");
    }

    @AfterEach
    void tearDown() {
//        accountService.deleteAllAccounts();
    }

    @Test
    void createNewAccount() {
        Account account = accountService.createNewAccount(createAccountRequest1);
        assertThat(account.getIban()).isNotNull();
    }

//    @Test
//    void verifyPin() {
//    }

    @Test
    void depositMoneyTest() {
        Account account = accountService.createNewAccount(createAccountRequest1);
        assertThat(account.getIban()).isNotNull();

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountId(account.getId());
        depositRequest.setAmount(BigDecimal.valueOf(20000.00));

        String depositResponse = accountService.depositMoney(depositRequest);
        assertThat(depositResponse).isEqualTo("Transaction successful");

        assertThat(accountService.getBalance(account.getId(),
                createAccountRequest1.getPin())).isEqualTo(BigDecimal.valueOf(20000.00).setScale(2));
    }

    @Test
    void withdrawMoneyTest() {
        Account account = accountService.createNewAccount(createAccountRequest1);
        assertThat(account.getIban()).isNotNull();

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountId(account.getId());
        depositRequest.setAmount(BigDecimal.valueOf(20000.00));

        String depositResponse = accountService.depositMoney(depositRequest);
        assertThat(depositResponse).isEqualTo("Transaction successful");

        assertThat(accountService.getBalance(account.getId(),
                createAccountRequest1.getPin())).isEqualTo(BigDecimal.valueOf(20000.00).setScale(2));

        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountId(account.getId());
        withdrawRequest.setAmount(BigDecimal.valueOf(10000.00));
        withdrawRequest.setPin("1234");

        String withdrawResponse = accountService.withdrawMoney(withdrawRequest);
        assertThat(withdrawResponse).isEqualTo("Transaction successful");

        assertThat(accountService.getBalance(account.getId(),
                withdrawRequest.getPin())).isEqualTo(BigDecimal.valueOf(10000.00).setScale(2));
    }

    @Test
    void transferMoney() {
        Account account1 = accountService.createNewAccount(createAccountRequest1);
        assertThat(account1.getIban()).isNotNull();
        Account account2 = accountService.createNewAccount(createAccountRequest2);
        assertThat(account2.getIban()).isNotNull();
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountId(account1.getId());
        depositRequest.setAmount(BigDecimal.valueOf(20000.00));

        String depositResponse = accountService.depositMoney(depositRequest);
        assertThat(depositResponse).isEqualTo("Transaction successful");

        assertThat(accountService.getBalance(account1.getId(),
                createAccountRequest1.getPin())).isEqualTo(BigDecimal.valueOf(20000.00).setScale(2));

        assertThat(accountService.getBalance(account2.getId(),
                createAccountRequest2.getPin())).isEqualTo(BigDecimal.valueOf(0));

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setAccountId(account1.getId());
        transferRequest.setRecipientIban(account2.getIban());
        transferRequest.setAmount(BigDecimal.valueOf(10000.00));
        transferRequest.setPin(createAccountRequest1.getPin());

        String transferResponse = accountService.transferMoney(transferRequest);
        assertThat(transferResponse).isEqualTo("Transaction successful");

        assertThat(accountService.getBalance(account1.getId(),
                createAccountRequest1.getPin())).isEqualTo(BigDecimal.valueOf(10000.00).setScale(2));

        assertThat(accountService.getBalance(account2.getId(),
                createAccountRequest2.getPin())).isEqualTo(BigDecimal.valueOf(10000.00).setScale(2));
    }

    @Test
    void getBalance() {
    }
}