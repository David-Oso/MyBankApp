package com.bank.MyBankApp.bank.service;

import com.bank.MyBankApp.bank.model.Bank;
import com.bank.MyBankApp.bank.response.BankResponse;

public interface BankService {
    BankResponse getBankById(Integer id);
    Bank getBankByBankId(Integer bankId);
    BankResponse getBankByBankCode(String bankCode);
}
