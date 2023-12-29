package com.bank.MyBankApp.customer.dto.response;

import com.bank.MyBankApp.appUser.dto.response.JwtResponse;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponse {
    private String message;
    private JwtResponse jwtResponse;
}
