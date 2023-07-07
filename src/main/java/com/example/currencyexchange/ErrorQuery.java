package com.example.currencyexchange;

public class ErrorQuery {

    private String errorMessage;

    public ErrorQuery(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
