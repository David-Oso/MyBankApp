package com.bank.MyBankApp.otp.service;

import com.bank.MyBankApp.otp.OtpRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpServiceImpl implements OtpService{
    private final OtpRepository otpRepository;
}
