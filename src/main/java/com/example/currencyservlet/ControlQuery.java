package com.example.currencyservlet;

import com.example.currencyexchange.Currency;
import com.example.currencyexchange.ExchangeRates;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class ControlQuery {

    private ObjectMapper objectMapper = new ObjectMapper();
    private CurrencyDAO currencyDAO = new CurrencyDAO();


    public void getCurrency(String s, HttpServletResponse response) {

//        Успех - 200
//        Валюта не найдена - 404
//        Ошибка (например, база данных недоступна) - 500

        if (s.equals("USD")) {
            try {
                Currency currencyByCode = currencyDAO.getCurrencyByCode(s);
                String jsonCurrency = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(currencyByCode);
                PrintWriter writer = response.getWriter();
                writer.println(jsonCurrency);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.setStatus(400);
            try {
                response.sendError(400, "Err");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getAllCurrency(HttpServletResponse response) throws IOException {
        ArrayList<Currency> allCurrency = currencyDAO.getAllCurrency();
        String[] all = new String[allCurrency.size()];
        for (int i = 0; i < all.length; i++) {
            all[i] = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(allCurrency.get(i));
        }

        PrintWriter writer = response.getWriter();
        writer.println(Arrays.toString(all));



//        for (Currency currency : allCurrency) {
//            String jsonCurrency = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(currency);
//            PrintWriter writer = response.getWriter();
//            writer.println(jsonCurrency);
//        }







//        try {
//            PrintWriter pw = response.getWriter();
//            pw.println("[" + "<br>");
//            for (int i = 0; i < allCurrency.size(); i++) {
//                if (i == allCurrency.size() - 1) {
//                    pw.println(allCurrency.get(i).toString() + "<br>") ;
//                } else {
//                    pw.println(allCurrency.get(i).toString() + "," + "<br>");
//                }
//            }
//            pw.println("]");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

//ToDo сделать красивый JSON https://habr.com/ru/companies/otus/articles/687004/
//        HTTP коды ответов:
//
//        Успех - 200
//        Ошибка (например, база данных недоступна) - 500
    }

    public void getAllExchangeRates(HttpServletResponse response) throws IOException {

        ArrayList<ExchangeRates> exchangeRates = currencyDAO.getExchangeRates();
        String[] all = new String[exchangeRates.size()];

        for (int i = 0; i < all.length; i++) {
            all[i] = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(exchangeRates.get(i));
        }

        PrintWriter writer = response.getWriter();
        writer.println(Arrays.toString(all));


//        HTTP коды ответов:
//
//        Успех - 200
//        Ошибка (например, база данных недоступна) - 500
    }
}
