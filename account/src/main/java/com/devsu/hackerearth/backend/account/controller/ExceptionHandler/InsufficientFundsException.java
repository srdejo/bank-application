package com.devsu.hackerearth.backend.account.controller.ExceptionHandler;

public class InsufficientFundsException extends RuntimeException  {
    public InsufficientFundsException(String message) {
        super(message);
    }
    
}
