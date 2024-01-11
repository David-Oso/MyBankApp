package com.bank.MyBankApp.otp.service;

import com.bank.MyBankApp.appUser.model.AppUser;
import com.bank.MyBankApp.exception.OtpException;
import com.bank.MyBankApp.otp.OtpEntity;
import com.bank.MyBankApp.otp.OtpRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpServiceImpl implements OtpService{
    private final OtpRepository otpRepository;

    @Override
    public String generateAndSaveOtp(AppUser appUser) {
        OtpEntity existingOtp = otpRepository.findByAppUser(appUser);
        if(existingOtp != null)
            otpRepository.delete(existingOtp);
        String otp = generateOtp();
        OtpEntity otpEntity = new OtpEntity();
        otpEntity.setOtp(otp);
        otpEntity.setAppUser(appUser);
        otpRepository.save(otpEntity);
        return otp;
    }

    private static String generateOtp() {
        final SecureRandom secureRandom = new SecureRandom();
        return secureRandom.ints(6, 0, 10)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }

    @Override
    public OtpEntity validateOtp(String otp, Integer appUserId) {
        OtpEntity otpEntity = otpRepository.findByOtpAndAppUserId(otp, appUserId);
        if(otpEntity == null)
            throw new OtpException("Otp is invalid");
        else if (otpEntity.getExpirationTime().isBefore(LocalDateTime.now())){
            otpRepository.delete(otpEntity);
            throw new OtpException("Otp is expired");
        }
        return otpEntity;
    }

    @Override
    public void deleteOtp(OtpEntity otpEntity) {
        otpRepository.delete(otpEntity);
    }
}
