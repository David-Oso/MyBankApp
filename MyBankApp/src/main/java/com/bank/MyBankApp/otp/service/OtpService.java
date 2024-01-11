package com.bank.MyBankApp.otp.service;

import com.bank.MyBankApp.appUser.model.AppUser;
import com.bank.MyBankApp.otp.OtpEntity;

public interface OtpService {
    String generateAndSaveOtp(AppUser appUser);
    OtpEntity validateOtp(String otp, Integer appUserId);
    void deleteOtp(OtpEntity otpEntity);
}
