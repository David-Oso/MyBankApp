package com.bank.MyBankApp.otp;

import com.bank.MyBankApp.appUser.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<OtpEntity, Integer> {
    OtpEntity findByAppUser(AppUser appUser);
    OtpEntity findByOtpAndAppUserId(String otp, Integer appUserId);
}
