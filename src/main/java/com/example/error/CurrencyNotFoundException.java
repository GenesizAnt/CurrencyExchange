package com.example.error;

public class CurrencyNotFoundException extends Exception {

    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
