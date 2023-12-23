package com.bank.MyBankApp.bank.service;

import com.bank.MyBankApp.bank.response.BankResponse;

public interface BankService {
    BankResponse getBankById(Integer id);
    BankResponse getBankByBankCode(String bankCode);
}
