package com.bank.MyBankApp.mail;

public interface MailService {
    void sendMail(String emailAddress, String subject, String htmlContent);
}
