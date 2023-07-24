package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Util {

    static ObjectMapper objectMapper;
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

    public static String[] getExchangeRatePair(String s) {
        return new String[]{s.substring(0, 3), s.substring(3, 6)};
    }

}


