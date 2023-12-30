package com.bank.MyBankApp.exception;

public class InvalidCredentialException extends MyBankException{
    public InvalidCredentialException(String message) {
        super(message);
    }
}
