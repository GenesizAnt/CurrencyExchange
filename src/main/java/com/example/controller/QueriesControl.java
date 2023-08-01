package com.example.controller;

import com.example.entity.Currency;
import com.example.entity.ErrorQuery;
import com.example.entity.ExchangeRate;
import com.example.entity.ExchangeTransaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.data.CurrencyDAO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static com.example.Util.getCurrenciesForExchange;
import static com.example.Util.getJsonResponse;

public class QueriesControl {

    private ObjectMapper objectMapper;
    private CurrencyDAO currencyDAO;
    private ErrorQuery errorQuery;

    public QueriesControl() {
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

        for (Currency currency : allCurrency) {
            getJsonResponse(currency, response);
        }

        response.setStatus(200);
    }

    public void getAllExchangeRates(HttpServletResponse response) throws IOException {
        ArrayList<ExchangeRate> exchangeRates;

        try {
            exchangeRates = currencyDAO.getAllExchangeRates();
        } catch (Exception e) {
            response.setStatus(500);
            errorQuery = new ErrorQuery("Database is unavailable - 500");
            getJsonResponse(errorQuery, response);
            throw new RuntimeException(e);
        }

        for (ExchangeRate rates : exchangeRates) {
            getJsonResponse(rates, response);
        }
        response.setStatus(200);
    }

    public void getExchangeRate(String codeExchangeRate, HttpServletResponse response) throws IOException {

        try {
            String[] codeCurrenciesForExchange = getCurrenciesForExchange(codeExchangeRate);
            ExchangeRate exchangeRate = currencyDAO.getExchangeRateByCode(codeCurrenciesForExchange[0], codeCurrenciesForExchange[1]);
            if (!(exchangeRate == null)) {
                response.setStatus(200);
                getJsonResponse(exchangeRate, response);
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

    public void getExchangeTransaction(String baseCurrency, String targetCurrency, String amount, HttpServletResponse response) throws IOException {

        double AmountForExchange = Double.parseDouble(amount);
        ExchangeRate exchangeDirectRate;
        ExchangeRate exchangeReverseRate;
        ArrayList<ExchangeRate> exchangesThroughUSDRate;

        try {
            exchangeDirectRate = currencyDAO.getExchangeRateByCode(baseCurrency, targetCurrency);
            exchangeReverseRate = currencyDAO.getExchangeRateByCode(targetCurrency, baseCurrency);
            exchangesThroughUSDRate = currencyDAO.getExchangeThroughTransaction(baseCurrency, targetCurrency);
        } catch (Exception e) {
            response.setStatus(500);
            errorQuery = new ErrorQuery("Database is unavailable - 500");
            getJsonResponse(errorQuery, response);
            throw new RuntimeException(e);
        }

        if (!(exchangeDirectRate == null)) {
            ExchangeTransaction exchangeTransaction = new ExchangeTransaction(exchangeDirectRate);
            exchangeTransaction.calculateExchangeTransaction(AmountForExchange);
            response.setStatus(200);
            getJsonResponse(exchangeTransaction, response);
        } else if (!(exchangeReverseRate == null)) {
            ExchangeTransaction exchangeTransaction = new ExchangeTransaction(exchangeReverseRate);
            exchangeTransaction.calculateReverseExchangeTransaction(AmountForExchange);
            response.setStatus(200);
            getJsonResponse(exchangeTransaction, response);
        } else if (!(exchangesThroughUSDRate == null)) {
            Currency base = currencyDAO.getCurrencyByCode(baseCurrency);
            Currency target = currencyDAO.getCurrencyByCode(targetCurrency);
            ExchangeTransaction exchangeTransaction = new ExchangeTransaction(base, target);
            exchangeTransaction.calculateExchangeTransactionThroughUSD(AmountForExchange, exchangesThroughUSDRate);
            response.setStatus(200);
            getJsonResponse(exchangeTransaction, response);
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
            } catch (IOException e) {
                response.setStatus(500);
                errorQuery = new ErrorQuery("Database is unavailable - 500");
                getJsonResponse(errorQuery, response);
                throw new RuntimeException(e);
            }
        }
    }

    public void postExchangeRate(String baseCurrencyCode, String targetCurrencyCode, String rate, HttpServletResponse response) throws IOException {

        Currency baseCurrency = currencyDAO.getCurrencyByCode(baseCurrencyCode);
        Currency targetCurrency = currencyDAO.getCurrencyByCode(targetCurrencyCode);

        ExchangeRate exchangeRate = currencyDAO.getExchangeRateByCode(baseCurrencyCode, targetCurrencyCode);

        if (!(exchangeRate == null)) {
            response.setStatus(409);
            errorQuery = new ErrorQuery("Currency with this code already exists - 409");
            getJsonResponse(errorQuery, response);
        } else {
            //Todo остановился рефакторинге тут
            currencyDAO.insertExchangeRate(baseCurrency, targetCurrency, rate);
            try {
                exchangeRate = currencyDAO.getExchangeRateByCode(baseCurrencyCode, targetCurrencyCode);
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

        String[] exchangeRatePair = getCurrenciesForExchange(exchangeRateCode);//Todo добавить проверку на нулл and another

        Currency baseCurrency = currencyDAO.getCurrencyByCode(exchangeRatePair[0]);
        Currency targetCurrency = currencyDAO.getCurrencyByCode(exchangeRatePair[1]);

        ExchangeRate exchangeRate = currencyDAO.getExchangeRateByCode(exchangeRatePair[0], exchangeRatePair[1]);

        if (exchangeRate == null) {
            response.setStatus(409);
            errorQuery = new ErrorQuery("Exchange transaction rate not found - 404");
            getJsonResponse(errorQuery, response);
        } else {
            currencyDAO.patchExchangeRate(baseCurrency, targetCurrency, rate);

            try {
                exchangeRate = currencyDAO.getExchangeRateByCode(exchangeRatePair[0], exchangeRatePair[1]);
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