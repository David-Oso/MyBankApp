package com.bank.MyBankApp.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
class MailServiceImplTest {
    @Autowired
    private MailService mailService;

    @Test
    void sendMail() {
        String email = "test@gmail.com";
        String subject = "Testing email service";
        String htmlContent = "This is just testing please ignore.";
        mailService.sendMail(email, subject, htmlContent);
    }
}