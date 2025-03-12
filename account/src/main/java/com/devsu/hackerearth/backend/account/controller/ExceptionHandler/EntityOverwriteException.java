package com.devsu.hackerearth.backend.account.controller.ExceptionHandler;

public class EntityOverwriteException extends RuntimeException  {
    public EntityOverwriteException(String message) {
        super(message);
    }
    
}
