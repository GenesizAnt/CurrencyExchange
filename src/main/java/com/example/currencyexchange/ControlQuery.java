package com.example.currencyexchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class ControlQuery {

    private ObjectMapper objectMapper;
    private CurrencyDAO currencyDAO;

    public ControlQuery() {
        this.objectMapper = new ObjectMapper();
        this.currencyDAO = new CurrencyDAO();
    }

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

        ArrayList<ExchangeRates> exchangeRates = currencyDAO.getAllExchangeRates();
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

    public void getExchangeRate(String s, HttpServletResponse response) {

        if (s.equals("USDRUB")) {
            try {
                String[] exchangeRatePair = getExchangeRatePair(s);
//                Currency currencyByCode = currencyDAO.getCurrencyByCode(s);
                ExchangeRates exchangeRates = (ExchangeRates) currencyDAO.getExchangeRate(exchangeRatePair[0], exchangeRatePair[1]); //Todo добавить проверку на нулл
                String jsonExchangeRate = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(exchangeRates);
                PrintWriter writer = response.getWriter();
                writer.println(jsonExchangeRate);
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


//        Успех - 200
//        Обменный курс для пары не найден - 404
//        Ошибка (например, база данных недоступна) - 500
    }

    private String[] getExchangeRatePair(String s) {
        return new String[]{s.substring(0, 3), s.substring(3, 6)};
    }

    public void getExchangeTransaction(String from, String to, String amount, HttpServletResponse response) throws IOException {

        //ToDo добавить проверку на корректность эмоунт

        PairCurrency exchangeTransaction = currencyDAO.getExchangeRate(from, to);
        PairCurrency exchangeReversRate = currencyDAO.getExchangeRate(to, from);
        ArrayList<PairCurrency> exchangeThroughRate = currencyDAO.getExchangeThroughTransaction(from, to);
//
        if (!(exchangeTransaction == null)) {
            ExchangeTransaction exchangeTransactionnnnn = new ExchangeTransaction(exchangeTransaction);
            exchangeTransactionnnnn.calculateExchange(amount);
            String jsonExchangeTransaction = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(exchangeTransactionnnnn);
            PrintWriter writer = response.getWriter();
            writer.println(jsonExchangeTransaction);
        } else if (!(exchangeReversRate == null)){
            ExchangeTransaction exchangeTransactionnnnn = new ExchangeTransaction(exchangeReversRate);
            exchangeTransactionnnnn.calculateReverseExchange(amount); // сделать обратный расчет курса
            String jsonExchangeTransaction = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(exchangeTransactionnnnn);
            PrintWriter writer = response.getWriter();
            writer.println(jsonExchangeTransaction);
        } else if (!(exchangeThroughRate == null)) {
            Currency base = currencyDAO.getCurrencyByCode(from);
            Currency target = currencyDAO.getCurrencyByCode(to);
            ExchangeTransaction exchangeTransactionnnnn = new ExchangeTransaction(base, target);
            exchangeTransactionnnnn.calculateThroughExchange(amount, exchangeThroughRate); // сделать расчет через дополнительный курс
            String jsonExchangeTransaction = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(exchangeTransactionnnnn);
            PrintWriter writer = response.getWriter();
            writer.println(jsonExchangeTransaction);
        } else {
            //курсы не найдены
        }
    }

    public void postCurrency(String codeCurrency, String nameCurrency, String signCurrency, HttpServletResponse response) {

        currencyDAO.insertCurrency(codeCurrency, nameCurrency, signCurrency);

        try {
            Currency currencyByCode = currencyDAO.getCurrencyByCode(codeCurrency);
            String jsonCurrency = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(currencyByCode);
            PrintWriter writer = response.getWriter();
            writer.println(jsonCurrency);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void postExchangeRate(String baseCurrencyCodeExc, String targetCurrencyCodeExc, String rateExc, HttpServletResponse response) {

        Currency baseCurrency = currencyDAO.getCurrencyByCode(baseCurrencyCodeExc);
        Currency targetCurrency = currencyDAO.getCurrencyByCode(targetCurrencyCodeExc);
        currencyDAO.insertExchangeRate(baseCurrency, targetCurrency, rateExc);

        try {
            ExchangeRates exchangeRate = (ExchangeRates) currencyDAO.getExchangeRate(baseCurrencyCodeExc, targetCurrencyCodeExc);
            String jsonExchangeRate = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(exchangeRate);
            PrintWriter writer = response.getWriter();
            writer.println(jsonExchangeRate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void patchExchangeRate(String exchangeRateCode, String rate, HttpServletResponse response) {

        String[] exchangeRatePair = getExchangeRatePair(exchangeRateCode);//Todo добавить проверку на нулл and another

        Currency baseCurrency = currencyDAO.getCurrencyByCode(exchangeRatePair[0]);
        Currency targetCurrency = currencyDAO.getCurrencyByCode(exchangeRatePair[1]);

        currencyDAO.patchExchangeRate(baseCurrency, targetCurrency, rate);

        try {
            ExchangeRates exchangeRate = (ExchangeRates) currencyDAO.getExchangeRate(exchangeRatePair[0], exchangeRatePair[1]);
            String jsonExchangeRate = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(exchangeRate);
            PrintWriter writer = response.getWriter();
            writer.println(jsonExchangeRate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
