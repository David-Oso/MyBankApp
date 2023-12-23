package com.bank.MyBankApp.mail;

import com.bank.MyBankApp.mail.mailRequest.Recipient;
import com.bank.MyBankApp.mail.mailRequest.SendEmailRequest;
import com.bank.MyBankApp.mail.mailRequest.Sender;
import com.bank.MyBankApp.utilities.MyBankAppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService{
    @Value("${bank.email}")
    private String bankEmail;
    @Value("${mail.api.key}")
    private String apiKey;
    @Value("${mail.url}")
    private String mailUrl;

    @Async
    @Override
    public String sendMail(String name, String email, String subject, String htmlContent) {
        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        Sender sender = new Sender(MyBankAppUtils.BANK_NAME, bankEmail);
        sendEmailRequest.setSender(sender);
        Recipient recipient = new Recipient(name, email);
        sendEmailRequest.setSubject(subject);
        sendEmailRequest.setContent(htmlContent);
        sendEmailRequest.getRecipients().add(recipient);
        try{
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("api-key", apiKey);
            httpHeaders.set("accept", APPLICATION_JSON_VALUE);
            RequestEntity<SendEmailRequest> entity =
                    new RequestEntity<>(sendEmailRequest, httpHeaders, HttpMethod.POST, URI.create(mailUrl));
            ResponseEntity<String> response = restTemplate.postForEntity(mailUrl, entity, String.class);
            log.info(":::::::::::::::::::EMAIL SENT SUCCESSFULLY:::::::::::::::::::");
            return response.getBody();
        }catch (Exception exception){
            throw new RuntimeException("Error sending mail");
        }

    }
}
