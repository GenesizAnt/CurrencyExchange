package com.example;

import javax.servlet.http.HttpServletRequest;

public class Util {

    public static String getCodeFromURL(HttpServletRequest request) {
        String url = request.getRequestURI();
        String[] splitURL = url.split("/");
        if (splitURL.length < 4) {
            return "";
        } else {
            return splitURL[splitURL.length - 1].toUpperCase();
        }
    }
}

