package com.example.error;

public class ExchangeRateNotFoundException extends Exception {

    public ExchangeRateNotFoundException(String message) {
        super(message);
    }
}
