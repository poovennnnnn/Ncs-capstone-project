package com.banking.exception;

public class JwtExpiredException extends Exception {

    public JwtExpiredException(String message) {
        super(message);
    }

    public JwtExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
    
    

}
