//package com.example.controller;
//
//import com.example.entity.Currency;
//import com.example.error.ErrorQuery;
//import com.example.entity.ExchangeRate;
//import com.example.data.ExchangeTransaction;
//import com.example.test.CurrencyDAObefore;
//
//import jakarta.servlet.http.*;
//import java.io.IOException;
//import java.util.ArrayList;
//
//import static com.example.Util.getCurrenciesForExchange;
//import static com.example.Util.getJsonResponse;
//
//public class QueriesControl {
//
//    private CurrencyDAObefore currencyDAObefore;
//    private ErrorQuery errorQuery;
//
//    public QueriesControl() {
//        this.currencyDAObefore = new CurrencyDAObefore();
//    }
//
//    public void getCurrency(String codeCurrency, HttpServletResponse response) throws IOException {
//
//        if (codeCurrency.isEmpty()) {
//            response.setStatus(400);
//            errorQuery = new ErrorQuery("No currency code in the address - 400");
//            getJsonResponse(errorQuery, response);
//        } else {
//            try {
//                Currency currencyByCode = currencyDAObefore.getCurrencyByCode(codeCurrency);
//                if (!(currencyByCode == null)) {
//                    response.setStatus(200);
//                    getJsonResponse(currencyByCode, response);
//                } else {
//                    response.setStatus(404);
//                    errorQuery = new ErrorQuery("Currency not found - 404");
//                    getJsonResponse(errorQuery, response);
//                }
//            } catch (IOException e) {
//                response.setStatus(500);
//                errorQuery = new ErrorQuery("Database is unavailable - 500");
//                getJsonResponse(errorQuery, response);
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    public void getAllCurrency(HttpServletResponse response) throws IOException {
//        ArrayList<Currency> allCurrency;
//
//        try {
//            allCurrency = currencyDAObefore.getAllCurrency();
//        } catch (Exception e) {
//            response.setStatus(500);
//            errorQuery = new ErrorQuery("Database is unavailable - 500");
//            getJsonResponse(errorQuery, response);
//            throw new RuntimeException(e);
//        }
//
//        for (Currency currency : allCurrency) {
//            getJsonResponse(currency, response);
//        }
//
//        response.setStatus(200);
//    }
//
//    public void getAllExchangeRates(HttpServletResponse response) throws IOException {
//        ArrayList<ExchangeRate> exchangeRates;
//
//        try {
//            exchangeRates = currencyDAObefore.getAllExchangeRates();
//        } catch (Exception e) {
//            response.setStatus(500);
//            errorQuery = new ErrorQuery("Database is unavailable - 500");
//            getJsonResponse(errorQuery, response);
//            throw new RuntimeException(e);
//        }
//
//        for (ExchangeRate rates : exchangeRates) {
//            getJsonResponse(rates, response);
//        }
//        response.setStatus(200);
//    }
//
//    public void getExchangeRate(String codeExchangeRate, HttpServletResponse response) throws IOException {
//
//        try {
//            String[] codeCurrenciesForExchange = getCurrenciesForExchange(codeExchangeRate);
//            ExchangeRate exchangeRate = currencyDAObefore.getExchangeRateByCode(codeCurrenciesForExchange[0], codeCurrenciesForExchange[1]);
//            if (!(exchangeRate == null)) {
//                response.setStatus(200);
//                getJsonResponse(exchangeRate, response);
//            } else {
//                response.setStatus(404);
//                errorQuery = new ErrorQuery("Exchange rate not found - 404");
//                getJsonResponse(errorQuery, response);
//            }
//        } catch (IOException e) {
//            response.setStatus(500);
//            errorQuery = new ErrorQuery("Database is unavailable - 500");
//            getJsonResponse(errorQuery, response);
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void getExchangeTransaction(String baseCurrency, String targetCurrency, String amount, HttpServletResponse response) throws IOException {
//
//        double AmountForExchange = Double.parseDouble(amount);
//        ExchangeRate exchangeDirectRate;
//        ExchangeRate exchangeReverseRate;
//        ArrayList<ExchangeRate> exchangesThroughUSDRate;
//
//        try {
//            exchangeDirectRate = currencyDAObefore.getExchangeRateByCode(baseCurrency, targetCurrency);
//            exchangeReverseRate = currencyDAObefore.getExchangeRateByCode(targetCurrency, baseCurrency);
//            exchangesThroughUSDRate = currencyDAObefore.getExchangeThroughTransaction(baseCurrency, targetCurrency);
//        } catch (Exception e) {
//            response.setStatus(500);
//            errorQuery = new ErrorQuery("Database is unavailable - 500");
//            getJsonResponse(errorQuery, response);
//            throw new RuntimeException(e);
//        }
//
//        if (!(exchangeDirectRate == null)) {
//            ExchangeTransaction exchangeTransaction = new ExchangeTransaction(exchangeDirectRate);
//            exchangeTransaction.calculateExchangeTransaction(AmountForExchange);
//            response.setStatus(200);
//            getJsonResponse(exchangeTransaction, response);
//        } else if (!(exchangeReverseRate == null)) {
//            ExchangeTransaction exchangeTransaction = new ExchangeTransaction(exchangeReverseRate);
//            exchangeTransaction.calculateReverseExchangeTransaction(AmountForExchange);
//            response.setStatus(200);
//            getJsonResponse(exchangeTransaction, response);
//        } else if (!(exchangesThroughUSDRate == null)) {
//            Currency base = currencyDAObefore.getCurrencyByCode(baseCurrency);
//            Currency target = currencyDAObefore.getCurrencyByCode(targetCurrency);
//            ExchangeTransaction exchangeTransaction = new ExchangeTransaction(base, target);
//            exchangeTransaction.calculateExchangeTransactionThroughUSD(AmountForExchange, exchangesThroughUSDRate);
//            response.setStatus(200);
//            getJsonResponse(exchangeTransaction, response);
//        } else {
//            response.setStatus(404);
//            errorQuery = new ErrorQuery("Exchange transaction rate not found - 404");
//            getJsonResponse(errorQuery, response);
//        }
//    }
//
//    public void postCurrency(String codeCurrency, String nameCurrency, String signCurrency, HttpServletResponse response) throws IOException {
//
//        Currency currencyByCode = currencyDAObefore.getCurrencyByCode(codeCurrency);
//        //ToDo В гит репозитории не следует класть папки с Tomcat
//
//        if (!(currencyByCode == null)) {
//            response.setStatus(409);
//            errorQuery = new ErrorQuery("Currency with this code already exists - 409");
//            getJsonResponse(errorQuery, response);
//        } else {
//
//            currencyDAObefore.insertCurrency(codeCurrency, nameCurrency, signCurrency);
//
//            try {
//                currencyByCode = currencyDAObefore.getCurrencyByCode(codeCurrency);
//                response.setStatus(200);
//                getJsonResponse(currencyByCode, response);
//            } catch (IOException e) {
//                response.setStatus(500);
//                errorQuery = new ErrorQuery("Database is unavailable - 500");
//                getJsonResponse(errorQuery, response);
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    public void postExchangeRate(String baseCurrencyCode, String targetCurrencyCode, String rate, HttpServletResponse response) throws IOException {
//
//        Currency baseCurrency = currencyDAObefore.getCurrencyByCode(baseCurrencyCode);
//        Currency targetCurrency = currencyDAObefore.getCurrencyByCode(targetCurrencyCode);
//
//        ExchangeRate exchangeRate = currencyDAObefore.getExchangeRateByCode(baseCurrencyCode, targetCurrencyCode);
//
//        if (!(exchangeRate == null)) {
//            response.setStatus(409);
//            errorQuery = new ErrorQuery("Currency with this code already exists - 409");
//            getJsonResponse(errorQuery, response);
//        } else if (baseCurrency == null) {
//            response.setStatus(404);
//            errorQuery = new ErrorQuery("Currency with " + baseCurrencyCode + " code does not exist - 404");
//            getJsonResponse(errorQuery, response);
//        } else if (targetCurrency == null) {
//            response.setStatus(404);
//            errorQuery = new ErrorQuery("Currency with " + targetCurrencyCode + " code does not exist - 404");
//            getJsonResponse(errorQuery, response);
//        } else {
//
//            currencyDAObefore.insertExchangeRate(baseCurrency, targetCurrency, rate);
//
//            try {
//                exchangeRate = currencyDAObefore.getExchangeRateByCode(baseCurrencyCode, targetCurrencyCode);
//                response.setStatus(200);
//                getJsonResponse(exchangeRate, response);
//            } catch (IOException e) {
//                response.setStatus(500);
//                errorQuery = new ErrorQuery("Database is unavailable - 500");
//                getJsonResponse(errorQuery, response);
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    public void patchExchangeRate(String exchangeRateCode, String rate, HttpServletResponse response) throws IOException {
//
//        String[] currenciesForExchange = getCurrenciesForExchange(exchangeRateCode);
//        ExchangeRate exchangeRate = currencyDAObefore.getExchangeRateByCode(currenciesForExchange[0], currenciesForExchange[1]);
//
//        Currency baseCurrency = currencyDAObefore.getCurrencyByCode(currenciesForExchange[0]);
//        Currency targetCurrency = currencyDAObefore.getCurrencyByCode(currenciesForExchange[1]);
//
//        if (exchangeRate == null) {
//            response.setStatus(404);
//            errorQuery = new ErrorQuery("Exchange rate not found - 404");
//            getJsonResponse(errorQuery, response);
//        } else {
//
//            currencyDAObefore.patchExchangeRate(baseCurrency, targetCurrency, rate);
//
//            try {
//                exchangeRate = currencyDAObefore.getExchangeRateByCode(currenciesForExchange[0], currenciesForExchange[1]);
//                response.setStatus(200);
//                getJsonResponse(exchangeRate, response);
//            } catch (IOException e) {
//                response.setStatus(500);
//                errorQuery = new ErrorQuery("Database is unavailable - 500");
//                getJsonResponse(errorQuery, response);
//                throw new RuntimeException(e);
//            }
//        }
//
//
//    }
//}