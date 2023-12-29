package com.bank.MyBankApp.appSecurity.service;

import com.bank.MyBankApp.appSecurity.jwtToken.MyBankJwtToken;
import com.bank.MyBankApp.appSecurity.jwtToken.MyBankJwtTokenRepository;
import com.bank.MyBankApp.appUser.model.AppUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final MyBankJwtTokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith("Bearer "))
            return;
        String jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByAccessToken(jwt)
                .orElse(null);
        deleteAppUserTokens(storedToken);
    }

    private void deleteAppUserTokens(MyBankJwtToken storedToken) {
        if(storedToken != null){
            AppUser appUser = storedToken.getAppUser();
            var tokens = tokenRepository.findAllByAppUserId(appUser.getId());
            tokenRepository.deleteAll(tokens);
            SecurityContextHolder.clearContext();
        }
    }
}
