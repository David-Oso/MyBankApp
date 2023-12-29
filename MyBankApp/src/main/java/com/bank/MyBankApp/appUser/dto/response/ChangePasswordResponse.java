package com.bank.MyBankApp.appUser.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChangePasswordResponse {
    private String message;
}
