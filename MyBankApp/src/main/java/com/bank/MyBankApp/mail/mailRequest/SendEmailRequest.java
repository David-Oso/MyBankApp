package com.bank.MyBankApp.mail.mailRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SendEmailRequest {
    @JsonProperty("sender")
    private Sender sender;
    @JsonProperty("to")
    private List<Recipient> recipients = new ArrayList<>();
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("htmlContent")
    private String content;
}
