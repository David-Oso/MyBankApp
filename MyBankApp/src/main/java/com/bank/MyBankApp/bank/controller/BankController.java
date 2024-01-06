package com.bank.MyBankApp.bank.controller;

import com.bank.MyBankApp.bank.response.BankResponse;
import com.bank.MyBankApp.bank.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/banks")
public class BankController {
    private final BankService bankService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password){
        return ResponseEntity.ok(bankService.login(email, password));
    }


    @GetMapping("get/{id}")
    public ResponseEntity<?> getBankById(@PathVariable Integer id){
        BankResponse response = bankService.getBankById(id);
        return ResponseEntity.ok(response);
    }
//
//    @GetMapping("get/bank-code")
//    public ResponseEntity<?> getBankByBankCode(@RequestParam String bankCode){
//        BankResponse response = bankService.getBankByBankCode(bankCode);
//        return ResponseEntity.ok(response);
//    }
}