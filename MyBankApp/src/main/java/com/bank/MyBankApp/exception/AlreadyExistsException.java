package com.bank.MyBankApp.exception;

public class AlreadyExistsException extends MyBankException{
    public AlreadyExistsException(String message) {
        super(message);
    }
}
