package com.bank.MyBankApp.bank.service;

import com.bank.MyBankApp.bank.response.BankResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class BankServiceImplTest {
    @Autowired
    private BankService bankService;

    @Test
    void getBankByIdTest() {
        BankResponse bankResponse = bankService.getBankById(1);
        assertThat(bankResponse.getName()).isEqualTo("First Bank of Nigeria");
        assertThat(bankResponse.getBankAddress().getState()).isEqualTo("Lagos");
        assertThat(bankResponse.getBankAddress().getCountry()).isEqualTo("Nigeria");
    }

    @Test
    void getBankByBankCodeTest() {
    }
}