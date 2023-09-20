package com.example.error;

public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
