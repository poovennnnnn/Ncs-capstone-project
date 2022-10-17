package com.banking.exception;

public class AccountAlreadyExistsException extends Exception {
    
    public AccountAlreadyExistsException(String message) {
        super(message);
    }

}
