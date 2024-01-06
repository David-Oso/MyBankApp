package com.bank.MyBankApp.appSecurity;

import lombok.Getter;

@Getter
public class WhiteList {
    public static String[] freeAccess() {
        return new String[]{
                "/customers/register",
                "/customers/add-customer-address/{id}",
                "/customers/verify",
                "/customers/login",
                "/customers/resend_verification_mail",
                "/banks/login",

        };
    }

    public static String[] swagger() {
        return new String[]{
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui/**",
                "webjars/**",
                "/swagger-ui.html"
        };
    }
}