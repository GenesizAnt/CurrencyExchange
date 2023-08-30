package com.example.error;

import jakarta.servlet.http.HttpServletResponse;

public class CurrencyNotFoundException extends Exception {

    private String errorMessage;

    public CurrencyNotFoundException(String s) {
        errorMessage = s;
    }

    public CurrencyNotFoundException() {

    }

    public void sendError(int codeError, String msgError, HttpServletResponse response) {
        response.setStatus(codeError);
        errorMessage = msgError;
    }

}
