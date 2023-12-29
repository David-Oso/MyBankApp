package com.bank.MyBankApp.appSecurity;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class MyBankAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull AuthenticationException authException) throws IOException, ServletException {
        log.error("Unauthorized: {}", authException.getMessage());

        if(!response.isCommitted()){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            try(PrintWriter writer = response.getWriter()){
                writer.write(generateErrorMessage(authException));
            }
        }else log.warn("Unauthorized request received, but response has already been commited.");

    }

    private String generateErrorMessage(AuthenticationException authException) {
        return String.format("{\"Unauthorized\": \"%s\"}", authException.getMessage());
    }
}
