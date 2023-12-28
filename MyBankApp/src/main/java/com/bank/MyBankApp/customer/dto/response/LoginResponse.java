package com.bank.MyBankApp.customer.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponse {
    private String message;
    private JwtResponse jwtResponse;
}
