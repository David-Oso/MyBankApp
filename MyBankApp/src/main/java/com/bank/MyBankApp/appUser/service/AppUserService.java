package com.bank.MyBankApp.appUser.service;

import com.bank.MyBankApp.appUser.dto.request.ChangePasswordRequest;
import com.bank.MyBankApp.appUser.dto.response.ChangePasswordResponse;
import com.bank.MyBankApp.appUser.dto.response.JwtResponse;
import com.bank.MyBankApp.appUser.model.AppUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Principal;

public interface AppUserService {
    ChangePasswordResponse changePassword(ChangePasswordRequest changePasswordRequest, Principal connectedUser);
    JwtResponse generateJwtToken(AppUser appUser);
    AppUser getSecuredUser(Principal principal);
    AppUser authenticate(String email, String password);
    void revokeAllUserTokens(AppUser appUser);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
