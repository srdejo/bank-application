package com.devsu.hackerearth.backend.account.controller.ExceptionHandler;

public class NotFoundException extends RuntimeException  {
    public NotFoundException(String message) {
        super(message);
    }
}
