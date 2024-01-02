package com.bank.MyBankApp.account.controller;

import com.bank.MyBankApp.account.dto.request.CreateAccountRequest;
import com.bank.MyBankApp.account.dto.request.DepositRequest;
import com.bank.MyBankApp.account.dto.request.TransferRequest;
import com.bank.MyBankApp.account.dto.request.WithdrawRequest;
import com.bank.MyBankApp.account.dto.response.CreateAccountResponse;
import com.bank.MyBankApp.account.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bank.MyBankApp.utilities.ValidationUtils.PIN_REGEX;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;


    @PostMapping("create")
    public ResponseEntity<?> createAccount(@Valid @RequestBody CreateAccountRequest request){
        CreateAccountResponse response = accountService.createNewAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("deposit")
    public ResponseEntity<?> deposit(@Valid @RequestBody DepositRequest depositRequest){
        return ResponseEntity.ok(accountService.depositMoney(depositRequest));
    }

    @PostMapping("withdraw")
    public ResponseEntity<?> withdraw(@Valid @RequestBody WithdrawRequest request){
        return ResponseEntity.ok(accountService.withdrawMoney(request));
    }

    @PostMapping("transfer")
    public ResponseEntity<?> transfer(@Valid @RequestBody TransferRequest request){
        return ResponseEntity.ok(accountService.transferMoney(request));
    }

    @GetMapping("get-balance/{accountId}")
    public ResponseEntity<?> getBalance(@PathVariable  Integer accountId,
                                        @Pattern(regexp = PIN_REGEX, message = "Enter your 4 digits pin")
                                        @NotNull @RequestParam String pin){
        return ResponseEntity.ok(accountService.getBalance(accountId, pin));
    }
}
