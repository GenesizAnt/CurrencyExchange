package com.example;

import com.example.error.ValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class Util {

    public static final int CORRECT_COUNT_LETTER_CURRENCY_NAME = 3;
    public static final int CODE_POSITION_IN_URL = 6;
    public static final int CORRECT_COUNT_LETTER_EXCHANGE_RATE_NAME = 6;

    static ObjectMapper objectMapper = new ObjectMapper();
    private static PrintWriter writer;

    public static void getJsonResponse(HttpServletResponse response, int codeResponse, Object obj) throws IOException {
        String jsonResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        writer = response.getWriter();
        response.setStatus(codeResponse);
        writer.println(jsonResponse);
    }

    public static String getCodeFromURL(HttpServletRequest request) throws ValidationException {
        String[] splitURL = getSplitURL(request);
        String code = splitURL[splitURL.length - 1].toUpperCase();
        try {
            if (code.length() > CORRECT_COUNT_LETTER_CURRENCY_NAME) {
                throw new ValidationException("No currency code in the address - 400");
            } else {
                return code;
            }
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }
    }

//    public static void getJsonResponse(Object obj, HttpServletResponse response) throws IOException {
////        Optional<Currency> objectOptional = obj;
//        String jsonResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
//        PrintWriter writer = response.getWriter();
//        writer.println(jsonResponse);
//    }

    public static String[] getCurrenciesForExchange(String CodeCurrenciesForExchange) {
        return new String[]{CodeCurrenciesForExchange.substring(0, 3), CodeCurrenciesForExchange.substring(3, 6)};
    }

    public static boolean isCorrectCodeCurrency(HttpServletRequest request) throws ValidationException {
        String[] splitURL = getSplitURL(request);
        try {
            if (isCorrectCode(splitURL, CORRECT_COUNT_LETTER_CURRENCY_NAME)) {
                return true;
            } else {
                throw new ValidationException("No currency code in the address - 400");
            }
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    public static boolean isCorrectCodeExchangeRate(HttpServletRequest request) {
        String[] splitURL = getSplitURL(request);
        return isCorrectCode(splitURL, CORRECT_COUNT_LETTER_EXCHANGE_RATE_NAME);
    }

    private static String[] getSplitURL(HttpServletRequest request) {
        String url = request.getRequestURI();
        return url.split("/");
    }

    private static boolean isCorrectCode(String[] splitURL, int correctLetterCountCurrentName) {
        String codeRequest = splitURL[splitURL.length - 1].toUpperCase();

        if (!(splitURL.length < CODE_POSITION_IN_URL)) {
            return false;
        }
        if (!(codeRequest.length() == correctLetterCountCurrentName)) {
            return false;
        }
        if (codeRequest.matches("\\d+")) {
            return false;
        }
        if (!(codeRequest.matches("[a-zA-Z]+"))) {
            return false;
        }
        return true;
    }

}


