package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Util {

    public static final int CORRECT_LETTER_COUNT_CURRENT_NAME = 3;
    public static final int CODE_POSITION_IN_URL = 6;//4
    public static final int CORRECT_LETTER_COUNT_EXCHANGE_RATE_NAME = 6;

    static ObjectMapper objectMapper = new ObjectMapper();
    public static String getCodeFromURL(HttpServletRequest request) {
//        String url = request.getRequestURI();
//        String[] splitURL = url.split("/");
        String[] splitURL = getSplitURL(request);
        return splitURL[splitURL.length - 1].toUpperCase();
//        if (splitURL.length < CODE_POSITION_IN_URL) {
//            return new String[]{};
//        } else {
//            return getExchangeRatePair(splitURL[splitURL.length - 1].toUpperCase());
//        }
    }

    public static void getJsonResponse(Object obj, HttpServletResponse response) throws IOException {
        String jsonRes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        PrintWriter writer = response.getWriter();
        writer.println(jsonRes);
    }

    public static String[] getExchangeRatePair(String exchangeRate) {
        return new String[]{exchangeRate.substring(0, 3), exchangeRate.substring(3, 6)};
//        if (!(exchangeRate.length() == CORRECT_LETTER_COUNT_EXCHANGE_RATE_NAME)) {
//            return new String[]{exchangeRate.substring(0, 3), exchangeRate.substring(3, 6)};
//        } else {
//            return new String[]{};
//        }
    }

    public static boolean checkAmount(String amount) {
        if (Integer.valueOf(amount) != null) {
            int num = Integer.parseInt(amount);
            return true;
        } else {
            return false;
        }
    }

    public static boolean isCorrectCodeCurrency(HttpServletRequest request) {
//        String url = request.getRequestURI();
//        String[] splitURL = url.split("/");
        String[] splitURL = getSplitURL(request);
        return isCorrectCode(splitURL, CORRECT_LETTER_COUNT_CURRENT_NAME);
    }

    public static boolean isCorrectCodeExchangeRates(HttpServletRequest request) {
//        String url = request.getRequestURI();
//        String[] splitURL = url.split("/");
        String[] splitURL = getSplitURL(request);
        return isCorrectCode(splitURL, CORRECT_LETTER_COUNT_EXCHANGE_RATE_NAME);
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


