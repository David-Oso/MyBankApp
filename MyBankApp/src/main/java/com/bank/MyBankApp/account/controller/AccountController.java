package com.bank.MyBankApp.account.controller;

import com.bank.MyBankApp.account.dto.request.*;
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

    @GetMapping("get-account-transactions/{accountId}")
    public ResponseEntity<?> getAccountTransactions(@PathVariable Integer accountId){
        return ResponseEntity.ok(accountService.getAccountTransactions(accountId));
    }

    @GetMapping("get-account-transaction-by-time-range")
    public ResponseEntity<?> getAccountTransactionByTimeRange(@Valid @RequestBody
                                                                   TransactionByTimeRangeRequest request){
        return ResponseEntity.ok(accountService.getTransactionByAccountIdAndTimeRange(request));
    }

    @DeleteMapping("delete/{accountId}/{customerId}")
    public ResponseEntity<Void> deleteAccountByAccountAndCustomerId(@PathVariable Integer accountId,
                                                                    @PathVariable Integer customerId){
        accountService.deleteAccountByAccountAndCustomerId(accountId, customerId);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("delete-all/{customerId}")
    public ResponseEntity<Void> deleteAllAccountsByCustomerId(@PathVariable Integer customerId){
        accountService.deleteAllAccountByCustomerId(customerId);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("delete-all")
    public ResponseEntity<Void> deleteAllAccounts(){
        accountService.deleteAllAccounts();
        return ResponseEntity.accepted().build();
    }
}
