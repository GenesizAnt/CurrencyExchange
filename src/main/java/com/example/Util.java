package com.example;

import com.example.error.CurrencyNotFoundException;
import com.example.error.ValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static java.util.Map.entry;

public class Util {

    public static final int CORRECT_COUNT_LETTER_CURRENCY_NAME = 3;
    public static final int CODE_POSITION_IN_URL = 5;//6
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
        return splitURL[splitURL.length - 1].toUpperCase();
//        try {
//            if (code.length() > CORRECT_COUNT_LETTER_CURRENCY_NAME) {
//                throw new ValidationException("No currency code in the address - 400");
//            } else {
//                return code;
//            }
//        } catch (ValidationException e) {
//            throw new ValidationException(e.getMessage());
//        }
    }

    public static Map<String, String> checkRequestParameterForCurrency(HttpServletRequest request) throws ValidationException {
        String empty = "";
        String codeCurrency = request.getParameter("code");
        String nameCurrency = request.getParameter("name");
        String signCurrency = request.getParameter("sign");

        if (codeCurrency.equals(empty) || codeCurrency.length() != CORRECT_COUNT_LETTER_CURRENCY_NAME ||
                codeCurrency.matches("\\d+") || !(codeCurrency.matches("[a-zA-Z]+"))) {
            throw new ValidationException("Code currency " + codeCurrency + " is empty or incorrect - 400");
        } else if (nameCurrency.equals(empty)) {
            throw new ValidationException("Name currency " + nameCurrency + " is empty or incorrect - 400");
        } else if (signCurrency.equals(empty) || !(signCurrency.matches("\\p{ASCII}")) || signCurrency.matches("\\d+")) {
            throw new ValidationException("Sign currency " + signCurrency + " is empty or incorrect - 400");
        } else {
            return Map.ofEntries(
                    entry("code", codeCurrency),
                    entry("name", nameCurrency),
                    entry("sign", signCurrency));
        }
    }

    public static Map<String, String> checkRequestParameterForExchangeRate(HttpServletRequest request) throws ValidationException {
        String empty = "";
        String baseCurrencyCode = request.getParameter("baseCurrencyCode");
        String targetCurrencyCode = request.getParameter("targetCurrencyCode");
        String rate = request.getParameter("rate");

        if (baseCurrencyCode.equals(empty) || baseCurrencyCode.length() != CORRECT_COUNT_LETTER_CURRENCY_NAME ||
                baseCurrencyCode.matches("\\d+") || !(baseCurrencyCode.matches("[a-zA-Z]+"))) {
            throw new ValidationException("Base currency code" + baseCurrencyCode + " is empty or incorrect - 400");
        } else if (targetCurrencyCode.equals(empty) || targetCurrencyCode.length() != CORRECT_COUNT_LETTER_CURRENCY_NAME ||
                targetCurrencyCode.matches("\\d+") || !(targetCurrencyCode.matches("[a-zA-Z]+"))) {
            throw new ValidationException("Target currency code" + targetCurrencyCode + " is empty or incorrect - 400");
        } else if (rate.equals(empty) || !(rate.matches("^\\d+\\.\\d{1,6}$")) || rate.matches("[a-zA-Zа-яА-Я]+")) {
            throw new ValidationException("Rate exchange" + rate + " is empty or incorrect - 400");
        } else {
            return Map.ofEntries(
                    entry("baseCurrencyCode", baseCurrencyCode),
                    entry("targetCurrencyCode", targetCurrencyCode),
                    entry("rate", rate));
        }
    }

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

    public static boolean isCorrectCodeExchangeRate(HttpServletRequest request) throws ValidationException {
        String[] splitURL = getSplitURL(request);
        try {
            if (isCorrectCode(splitURL, CORRECT_COUNT_LETTER_EXCHANGE_RATE_NAME)) {
                return true;
            } else {
                throw new ValidationException("Pair currency codes are missing from the address - 400");
            }
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }
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


