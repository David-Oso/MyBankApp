package com.bank.MyBankApp.mail;

import com.bank.MyBankApp.mail.mailRequest.SendEmailRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class MailServiceImplTest {
    @Autowired
    private MailService mailService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void sendMail() {
        String name = "Temx";
        String email = "test@gmail.com";
        String subject = "Testing email service";
        String htmlContent = "<p>This is just testing pleae ignore.</p>";
        mailService.sendMail(name, email, subject, htmlContent);
    }
}