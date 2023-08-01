package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Util {

    public static final int CORRECT_COUNT_LETTER_CURRENCY_NAME = 3;
    public static final int CODE_POSITION_IN_URL = 6;
    public static final int CORRECT_COUNT_LETTER_EXCHANGE_RATE_NAME = 6;

    static ObjectMapper objectMapper = new ObjectMapper();

    public static String getCodeFromURL(HttpServletRequest request) {
        String[] splitURL = getSplitURL(request);
        return splitURL[splitURL.length - 1].toUpperCase();
    }

    public static void getJsonResponse(Object obj, HttpServletResponse response) throws IOException {
        String jsonResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        PrintWriter writer = response.getWriter();
        writer.println(jsonResponse);
    }

    public static String[] getCurrenciesForExchange(String CodeCurrenciesForExchange) {
        return new String[]{CodeCurrenciesForExchange.substring(0, 3), CodeCurrenciesForExchange.substring(3, 6)};
    }

    public static boolean isCorrectCodeCurrency(HttpServletRequest request) {
        String[] splitURL = getSplitURL(request);
        return isCorrectCode(splitURL, CORRECT_COUNT_LETTER_CURRENCY_NAME);
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


