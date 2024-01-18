package com.bank.MyBankApp.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService{
    private final JavaMailSender javaMailSender;

    @Value(("${spring.mail.username}"))
    private String senderEmail;

    @Async
    @Override
    public void sendMail(String emailAddress, String subject, String htmlContent) {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom(senderEmail, "First Bank Of Nigeria");
            messageHelper.setTo(emailAddress);
            messageHelper.setSubject(subject);
            messageHelper.setText(htmlContent, true);
            javaMailSender.send(message);
            log.info(":::::::::::::::::::EMAIL SENT SUCCESSFULLY:::::::::::::::::::");
        }catch (MessagingException | UnsupportedEncodingException exception){
            throw new RuntimeException("Error Sending Email");
        }
    }
}