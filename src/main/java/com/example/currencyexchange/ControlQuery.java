package com.example.currencyexchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.Util.getExchangeRatePair;
import static com.example.Util.getJsonResponse;

public class ControlQuery {

    private ObjectMapper objectMapper;
    private CurrencyDAO currencyDAO;
    private ErrorQuery errorQuery;

    public ControlQuery() {
        this.objectMapper = new ObjectMapper();
        this.currencyDAO = new CurrencyDAO();
    }

    public void getCurrency(String codeCurrency, HttpServletResponse response) throws IOException {

        if (codeCurrency.isEmpty()) {
            response.setStatus(400);
            errorQuery = new ErrorQuery("No currency code in the address - 400");
            getJsonResponse(errorQuery, response);
        } else {
            try {
                Currency currencyByCode = currencyDAO.getCurrencyByCode(codeCurrency);
                if (!(currencyByCode == null)) {
                    response.setStatus(200);
                    getJsonResponse(currencyByCode, response);
                } else {
                    response.setStatus(404);
                    errorQuery = new ErrorQuery("Currency not found - 404");
                    getJsonResponse(errorQuery, response);
                }
            } catch (IOException e) {
                response.setStatus(500);
                errorQuery = new ErrorQuery("Database is unavailable - 500");
                getJsonResponse(errorQuery, response);
                throw new RuntimeException(e);
            }
        }
    }

    public void getAllCurrency(HttpServletResponse response) throws IOException {
        ArrayList<Currency> allCurrency;

        try {
            allCurrency = currencyDAO.getAllCurrency();
        } catch (Exception e) {
            response.setStatus(500);
            errorQuery = new ErrorQuery("Database is unavailable - 500");
            getJsonResponse(errorQuery, response);
            throw new RuntimeException(e);
        }

        PrintWriter writer = response.getWriter();
        for (Currency currency : allCurrency) {
            writer.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(currency));
        }
        response.setStatus(200);
    }

    public void getAllExchangeRates(HttpServletResponse response) throws IOException {
        ArrayList<ExchangeRates> exchangeRates;

        try {
            exchangeRates = currencyDAO.getAllExchangeRates();
        } catch (Exception e) {
            response.setStatus(500);
            errorQuery = new ErrorQuery("Database is unavailable - 500");
            getJsonResponse(errorQuery, response);
            throw new RuntimeException(e);
        }

        PrintWriter writer = response.getWriter();
        for (ExchangeRates rates : exchangeRates) {
            writer.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rates));//ToDo вставить статик метод
        }
        response.setStatus(200);
    }

    public void getExchangeRate(String s, HttpServletResponse response) throws IOException {

        if (s.isEmpty()) {
            response.setStatus(400);
            errorQuery = new ErrorQuery("No exchange rate code in the address - 400");
            getJsonResponse(errorQuery, response);
        } else {
            try {
                String[] exchangeRatePair = getExchangeRatePair(s);
                ExchangeRates exchangeRates = currencyDAO.getExchangeRate(exchangeRatePair[0], exchangeRatePair[1]);
                if (!(exchangeRates == null)) {
                    response.setStatus(200);
                    getJsonResponse(exchangeRates, response);
                } else {
                    response.setStatus(404);
                    errorQuery = new ErrorQuery("Exchange rate not found - 404");
                    getJsonResponse(errorQuery, response);
                }
            } catch (IOException e) {
                response.setStatus(500);
                errorQuery = new ErrorQuery("Database is unavailable - 500");
                getJsonResponse(errorQuery, response);
                throw new RuntimeException(e);
            }
        }
    }

    public void getExchangeTransaction(String from, String to, String amount, HttpServletResponse response) throws IOException {

        int amountPars;
        try {
            amountPars = Integer.parseInt(amount);
        } catch (NumberFormatException e) {
            response.setStatus(500);
            errorQuery = new ErrorQuery("Database is unavailable - 500");//ToDo правильно указать ошибку
            getJsonResponse(errorQuery, response);
            throw new RuntimeException(e);
        }

        ExchangeRates exchangeTransaction = currencyDAO.getExchangeRate(from, to);
        ExchangeRates exchangeReversRate = currencyDAO.getExchangeRate(to, from);
        ArrayList<ExchangeRates> exchangeThroughRate = currencyDAO.getExchangeThroughTransaction(from, to);

        if (!(exchangeTransaction == null)) {
            ExchangeTransaction exchangeTransactionnnnn = new ExchangeTransaction(exchangeTransaction);
            exchangeTransactionnnnn.calculateExchange(amountPars);
            String jsonExchangeTransaction = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(exchangeTransactionnnnn);
            PrintWriter writer = response.getWriter();
            writer.println(jsonExchangeTransaction);
        } else if (!(exchangeReversRate == null)) {
            ExchangeTransaction exchangeTransactionnnnn = new ExchangeTransaction(exchangeReversRate);
            exchangeTransactionnnnn.calculateReverseExchange(amountPars); // сделать обратный расчет курса
            String jsonExchangeTransaction = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(exchangeTransactionnnnn);
            PrintWriter writer = response.getWriter();
            writer.println(jsonExchangeTransaction);
        } else if (!(exchangeThroughRate == null)) {
            Currency base = currencyDAO.getCurrencyByCode(from);
            Currency target = currencyDAO.getCurrencyByCode(to);
            ExchangeTransaction exchangeTransactionnnnn = new ExchangeTransaction(base, target);
            exchangeTransactionnnnn.calculateThroughExchange(amountPars, exchangeThroughRate); // сделать расчет через дополнительный курс
            String jsonExchangeTransaction = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(exchangeTransactionnnnn);
            PrintWriter writer = response.getWriter();
            writer.println(jsonExchangeTransaction);
        } else {
            response.setStatus(404);
            errorQuery = new ErrorQuery("Exchange transaction rate not found - 404");
            getJsonResponse(errorQuery, response);
        }
    }

    public void postCurrency(String codeCurrency, String nameCurrency, String signCurrency, HttpServletResponse response) throws IOException {

        Currency currencyByCode = currencyDAO.getCurrencyByCode(codeCurrency);

        if (!(currencyByCode == null)) {
            response.setStatus(409);
            errorQuery = new ErrorQuery("Currency with this code already exists - 409");
            getJsonResponse(errorQuery, response);
        } else {
            currencyDAO.insertCurrency(codeCurrency, nameCurrency, signCurrency);

            try {
                currencyByCode = currencyDAO.getCurrencyByCode(codeCurrency);
                response.setStatus(200);
                getJsonResponse(currencyByCode, response);
//            String jsonCurrency = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(currencyByCode);
//            PrintWriter writer = response.getWriter();
//            writer.println(jsonCurrency);
            } catch (IOException e) {
                response.setStatus(500);
                errorQuery = new ErrorQuery("Database is unavailable - 500");
                getJsonResponse(errorQuery, response);
                throw new RuntimeException(e);
            }

        }
    }

    public void postExchangeRate(String baseCurrencyCodeExc, String targetCurrencyCodeExc, String rateExc, HttpServletResponse response) throws IOException {

        Currency baseCurrency = currencyDAO.getCurrencyByCode(baseCurrencyCodeExc); //Todo проверить существуют ли такие валюты
        Currency targetCurrency = currencyDAO.getCurrencyByCode(targetCurrencyCodeExc);

        ExchangeRates exchangeRate = currencyDAO.getExchangeRate(baseCurrencyCodeExc, targetCurrencyCodeExc);

        if (!(exchangeRate == null)) {
            response.setStatus(409);
            errorQuery = new ErrorQuery("Currency with this code already exists - 409");
            getJsonResponse(errorQuery, response);
        } else {
            currencyDAO.insertExchangeRate(baseCurrency, targetCurrency, rateExc);
            try {
                exchangeRate = currencyDAO.getExchangeRate(baseCurrencyCodeExc, targetCurrencyCodeExc);
                response.setStatus(200);
                getJsonResponse(exchangeRate, response);
//                String jsonExchangeRate = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(exchangeRate);
//                PrintWriter writer = response.getWriter();
//                writer.println(jsonExchangeRate);
            } catch (IOException e) {
                response.setStatus(500);
                errorQuery = new ErrorQuery("Database is unavailable - 500");
                getJsonResponse(errorQuery, response);
                throw new RuntimeException(e);
            }
        }
    }

    public void patchExchangeRate(String exchangeRateCode, String rate, HttpServletResponse response) throws IOException {

        String[] exchangeRatePair = getExchangeRatePair(exchangeRateCode);//Todo добавить проверку на нулл and another

        Currency baseCurrency = currencyDAO.getCurrencyByCode(exchangeRatePair[0]);
        Currency targetCurrency = currencyDAO.getCurrencyByCode(exchangeRatePair[1]);

        ExchangeRates exchangeRate = currencyDAO.getExchangeRate(exchangeRatePair[0], exchangeRatePair[1]);

        if (exchangeRate == null) {
            response.setStatus(409);
            errorQuery = new ErrorQuery("Exchange transaction rate not found - 404");
            getJsonResponse(errorQuery, response);
        } else {
            currencyDAO.patchExchangeRate(baseCurrency, targetCurrency, rate);

            try {
                exchangeRate = currencyDAO.getExchangeRate(exchangeRatePair[0], exchangeRatePair[1]);
                response.setStatus(200);
                getJsonResponse(exchangeRate, response);
            } catch (IOException e) {
                response.setStatus(500);
                errorQuery = new ErrorQuery("Database is unavailable - 500");
                getJsonResponse(errorQuery, response);
                throw new RuntimeException(e);
            }
        }



    }
}

//Todo добавить везде проверку корректности ввода валют и прочее. Например добавить класс ЧекВалидЗапрос или в класс Ютил