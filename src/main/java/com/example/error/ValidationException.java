package com.example.error;

import jakarta.servlet.http.HttpServletResponse;

public class ValidationException extends Exception {

    private String errorMessage;

    public ValidationException(String s) {
//        errorMessage = s;
        super(s);
    }

//    public void sendError(int codeError, String msgError, HttpServletResponse response) {
//        response.setStatus(codeError);
//        errorMessage = msgError;
//    }

//    public String getErrorMessage() {
//        return errorMessage;
//    }
}