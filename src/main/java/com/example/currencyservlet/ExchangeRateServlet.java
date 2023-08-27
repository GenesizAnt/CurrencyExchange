//package com.example.currencyservlet;
//
//import com.example.controller.QueriesControl;
//import com.example.data.ErrorQuery;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//import java.io.IOException;
//
//import static com.example.Util.CORRECT_COUNT_LETTER_CURRENCY_NAME;
//import static com.example.Util.getJsonResponse;
//
//@WebServlet("/exchangeRates")
//public class ExchangeRateServlet extends HttpServlet {
//
//    private QueriesControl queriesControl = new QueriesControl();
//    private ErrorQuery errorQuery;
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        queriesControl.getAllExchangeRates(response);
//
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        String empty = "";
//
//        String baseCurrencyCode = request.getParameter("baseCurrencyCode");
//        String targetCurrencyCode = request.getParameter("targetCurrencyCode");
//        String rate = request.getParameter("rate");
//
//        if (baseCurrencyCode.equals(empty) || baseCurrencyCode.length() != CORRECT_COUNT_LETTER_CURRENCY_NAME ||
//                baseCurrencyCode.matches("\\d+") || !(baseCurrencyCode.matches("[a-zA-Z]+"))) {
//            response.setStatus(400);
//            errorQuery = new ErrorQuery("Base currency code" + baseCurrencyCode + " is empty or incorrect - 400");
//            getJsonResponse(errorQuery, response);
//        } else if (targetCurrencyCode.equals(empty) || targetCurrencyCode.length() != CORRECT_COUNT_LETTER_CURRENCY_NAME ||
//                targetCurrencyCode.matches("\\d+") || !(targetCurrencyCode.matches("[a-zA-Z]+"))) {
//            response.setStatus(400);
//            errorQuery = new ErrorQuery("Target currency code" + targetCurrencyCode + " is empty or incorrect - 400");
//            getJsonResponse(errorQuery, response);
//        } else if (rate.equals(empty) || !(rate.matches("^\\d+\\.\\d{1,6}$")) || rate.matches("[a-zA-Zа-яА-Я]+")) {
//            response.setStatus(400);
//            errorQuery = new ErrorQuery("Rate exchange" + rate + " is empty or incorrect - 400");
//            getJsonResponse(errorQuery, response);
//        } else {
//
//            queriesControl.postExchangeRate(baseCurrencyCode, targetCurrencyCode, rate, response);
//
//        }
//    }
//}
