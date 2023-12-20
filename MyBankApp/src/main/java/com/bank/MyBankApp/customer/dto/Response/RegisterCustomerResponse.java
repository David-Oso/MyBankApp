package com.bank.MyBankApp.customer.dto.Response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterCustomerResponse {
    private Integer customerId;
    private String message;
}
