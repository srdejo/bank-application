package com.devsu.hackerearth.backend.client.controller.ExceptionHandler;

public class NotFoundException extends RuntimeException  {
    public NotFoundException(String message) {
        super(message);
    }
}
