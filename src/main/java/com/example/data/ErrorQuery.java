package com.example.data;

public class ErrorQuery extends RuntimeException {

    private String errorMessage;

    public ErrorQuery(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
