package com.bank.MyBankApp.bank.service;

import com.bank.MyBankApp.appUser.dto.response.JwtResponse;
import com.bank.MyBankApp.bank.model.Bank;
import com.bank.MyBankApp.bank.response.BankResponse;
import com.bank.MyBankApp.branch.model.Branch;

public interface BankService {
    JwtResponse login(String bankEmail, String bankPassword);
    BankResponse getBankById(Integer id);
    Bank getBankByBankId(Integer bankId);
    BankResponse getBankByBankCode(String bankCode);
    String addNewBranch(Integer bankId, Branch branch);
    String approveBranch(Integer branchId);
}
