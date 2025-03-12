package com.devsu.hackerearth.backend.client.controller.ExceptionHandler;

public class EntityOverwriteException extends RuntimeException  {
    public EntityOverwriteException(String message) {
        super(message);
    }
    
}
