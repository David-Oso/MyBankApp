package com.bank.MyBankApp.mail;

public interface MailService {
//    void sendMail(String name, String email, String subject, String htmlContent);
    void sendHtmlMail(String emailAddress, String subject, String htmlContent);
}
