package com.bank.MyBankApp.bank.controller;

import com.bank.MyBankApp.bank.response.BankResponse;
import com.bank.MyBankApp.bank.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank")
public class BankController {
    private final BankService bankService;

    @GetMapping("get/id")
    public ResponseEntity<?> getBankById(@RequestParam Integer id){
        BankResponse response = bankService.getBankById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("get/bank-code")
    public ResponseEntity<?> getBankByBankCode(@RequestParam String bankCode){
        BankResponse response = bankService.getBankByBankCode(bankCode);
        return ResponseEntity.ok(response);
    }
}