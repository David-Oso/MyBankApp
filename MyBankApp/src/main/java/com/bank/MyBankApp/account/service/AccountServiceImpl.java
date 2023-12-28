package com.bank.MyBankApp.account.service;

import com.bank.MyBankApp.account.model.Account;
import com.bank.MyBankApp.account.repository.AccountRepository;
import com.bank.MyBankApp.account.request.CreateAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;


    @Override
    public Account createNewAccount(CreateAccountRequest request) {
//        Account account = new Account();
//        account.setAccountName(request.getAccountName());
//        String hashedPin = hashPin(request.getPin());
//        account.setAccountNumber();
//        account.setAccountPin();
//        account.setAccountType(request.getAccountType());
//        return account;
        return null;
    }

//    private static String hashPin(String pin) {
//    }
}

//public class PinEncoder {
//        String userPin = "1234";
//        try {
//            String hashedPin = hashPin(userPin);
//            System.out.println("Hashed PIN: " + hashedPin);
//            boolean isCorrect = verifyPin(userPin, hashedPin);
//            System.out.println("PIN Verification: " + isCorrect);
//        } catch (Jargon2Exception e) {
//            e.printStackTrace();
//        }
//    }
//    // Hash the PIN
//    private static String hashPin(String pin) throws Jargon2Exception {
//        return Jargon2.jargon2Hasher().hash(pin.toCharArray());
//    }
//    // Verify the PIN
//    private static boolean verifyPin(String pin, String hashedPin) throws Jargon2Exception {
//        return Jargon2.jargon2Verifier().verify(hashedPin, pin.toCharArray());
//    }
//}
