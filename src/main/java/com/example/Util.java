package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Util {

    static ObjectMapper objectMapper = new ObjectMapper();
    public static String getCodeFromURL(HttpServletRequest request) {
        String url = request.getRequestURI();
        String[] splitURL = url.split("/");
        if (splitURL.length < 4) {
            return "";
        } else {
            return splitURL[splitURL.length - 1].toUpperCase();
        }
    }

    public static void getJsonResponse(Object obj, HttpServletResponse response) throws IOException {
        String jsonRes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        PrintWriter writer = response.getWriter();
        writer.println(jsonRes);
    }

    public static String[] getExchangeRatePair(String exchangeRate) {
        return new String[]{exchangeRate.substring(0, 3), exchangeRate.substring(3, 6)};
    }

    public static boolean checkAmount(String amount) {

        if (Integer.valueOf(amount) != null) {
            int num = Integer.parseInt(amount);
            return true;
        } else {
            return false;
        }

//        try {
//            int amountPars = Integer.parseInt(amount);
//            return true;
//        } catch (NumberFormatException e) {
//            return false;
//        }
    }

}


