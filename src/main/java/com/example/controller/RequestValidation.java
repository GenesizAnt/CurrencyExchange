package com.example.controller;

import com.example.dto.ExchangeRateDTO;
import com.example.error.*;
import com.example.dto.CurrencyDTO;
import com.example.servise.CurrencyService;
import com.example.servise.ExchangeRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.Util.*;

public class RequestValidation extends HttpServlet {

    //ToDo Перед вставкой валюты в базу ты вручную проверяешь её существование, лучше положиться на UNIQUE индекс для колонки с кодом валюты
    //ToDo В гит репозитории не следует класть папки с Tomcat
    //ToDo создать ДТО и оправлять его на отдельную валидацию
    //ToDo ModelMapper что это для ДТО? Если нужно будет превращать одну дто в другую похожую, почитай про ModelMapper
    //ToDo Optional что это и как заменить НУЛЛ?

    //    CurrencyDAO currencyDAO = new CurrencyDAO();
    private CurrencyService currencyService = new CurrencyService();
    private ErrorQuery errorQuery;
    private ObjectMapper objectMapper = new ObjectMapper();
    private PrintWriter writer;
    private ExchangeRateService exchangeRateService = new ExchangeRateService();


    public void getCurrency(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            isCorrectCodeCurrency(request);
            String currencyCode = getCodeFromURL(request);
            try {
                Optional<CurrencyDTO> currencyDTO = currencyService.getCurrencyByCode(currencyCode);
                getJsonResponse(response, 200, currencyDTO.get());
            } catch (CurrencyNotFoundException e) {
                getJsonResponse(response, 404, e.getMessage());
            } catch (DatabaseException e) {
                getJsonResponse(response, 500, e.getMessage());
            }
        } catch (ValidationException e) {
            getJsonResponse(response, 400, e.getMessage());
        }
    }

    public void getAllCurrency(HttpServletResponse response) throws IOException {
        try {
            Optional<List<CurrencyDTO>> allCurrency = currencyService.getAllCurrency();
            getJsonResponse(response, 200, allCurrency.get());
        } catch (CurrencyNotFoundException e) {
            getJsonResponse(response, 404, e.getMessage());
        } catch (DatabaseException e) {
            getJsonResponse(response, 500, e.getMessage());
        }
    }

    public void postCurrency(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            Map<String, String> requestParameter = checkRequestParameter(request);

            try {
                currencyService.insertCurrency(requestParameter.get("code"),
                                               requestParameter.get("name"),
                                               requestParameter.get("sign"));

                Optional<CurrencyDTO> currencyDTO = currencyService.getCurrencyByCode(requestParameter.get("code"));
                getJsonResponse(response, 200, currencyDTO.get());

            } catch (DatabaseException e) {
                getJsonResponse(response, 409, e.getMessage());
            } catch (RuntimeException e) {
                getJsonResponse(response, 501, e.getMessage());
            }
        } catch (CurrencyNotFoundException e) {
            getJsonResponse(response, 400, e.getMessage());
        }
    }

    public void getAllExchangeRates(HttpServletResponse response) throws IOException {
        try {
            Optional<List<ExchangeRateDTO>> exchangeRateDTOList = exchangeRateService.getAllExchangeRates();
            getJsonResponse(response, 200, exchangeRateDTOList.get());
        } catch (ExchangeRateNotFoundException e) {
            getJsonResponse(response, 404, e.getMessage());
        } catch (DatabaseException e) {
            getJsonResponse(response, 500, e.getMessage());
        }
    }

    public void getExchangeRate(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            isCorrectCodeExchangeRate(request);
            String exchangeRateCode = getCodeFromURL(request);
            String[] currenciesForExchange = getCurrenciesForExchange(exchangeRateCode);

            try {
                Optional<ExchangeRateDTO> exchangeRateDTO = exchangeRateService.getExchangeRateByCode(currenciesForExchange[0],
                                                                                                      currenciesForExchange[1]);
                getJsonResponse(response, 200, exchangeRateDTO.get());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } catch (ValidationException e) {
            getJsonResponse(response, 400, e.getMessage());
        }


//        try {
//            String[] codeCurrenciesForExchange = getCurrenciesForExchange(codeExchangeRate);
//            ExchangeRate exchangeRate = currencyDAO.getExchangeRateByCode(codeCurrenciesForExchange[0], codeCurrenciesForExchange[1]);
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



//        if (isCorrectCodeExchangeRate(request)) {
//            String exchangeRateCode = getCodeFromURL(request);
//            queriesControl.getExchangeRate(exchangeRateCode, response);
//        } else {
//            response.setStatus(400);
//            errorQuery = new ErrorQuery("Incorrect request - 400");
//            getJsonResponse(errorQuery, response);
//        }
//

    }

//    public void getExchangeTransaction(String baseCurrency, String targetCurrency, String amount, HttpServletResponse response) throws IOException {
//
//        double AmountForExchange = Double.parseDouble(amount);
//        ExchangeRate exchangeDirectRate;
//        ExchangeRate exchangeReverseRate;
//        ArrayList<ExchangeRate> exchangesThroughUSDRate;
//
//        try {
//            exchangeDirectRate = currencyDAO.getExchangeRateByCode(baseCurrency, targetCurrency);
//            exchangeReverseRate = currencyDAO.getExchangeRateByCode(targetCurrency, baseCurrency);
//            exchangesThroughUSDRate = currencyDAO.getExchangeThroughTransaction(baseCurrency, targetCurrency);
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
//            Currency base = currencyDAO.getCurrencyByCode(baseCurrency);
//            Currency target = currencyDAO.getCurrencyByCode(targetCurrency);
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


//    public void postExchangeRate(String baseCurrencyCode, String targetCurrencyCode, String rate, HttpServletResponse response) throws IOException {
//
//        Currency baseCurrency = currencyDAO.getCurrencyByCode(baseCurrencyCode);
//        Currency targetCurrency = currencyDAO.getCurrencyByCode(targetCurrencyCode);
//
//        ExchangeRate exchangeRate = currencyDAO.getExchangeRateByCode(baseCurrencyCode, targetCurrencyCode);
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
//            currencyDAO.insertExchangeRate(baseCurrency, targetCurrency, rate);
//
//            try {
//                exchangeRate = currencyDAO.getExchangeRateByCode(baseCurrencyCode, targetCurrencyCode);
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

//    public void patchExchangeRate(String exchangeRateCode, String rate, HttpServletResponse response) throws IOException {
//
//        String[] currenciesForExchange = getCurrenciesForExchange(exchangeRateCode);
//        ExchangeRate exchangeRate = currencyDAO.getExchangeRateByCode(currenciesForExchange[0], currenciesForExchange[1]);
//
//        Currency baseCurrency = currencyDAO.getCurrencyByCode(currenciesForExchange[0]);
//        Currency targetCurrency = currencyDAO.getCurrencyByCode(currenciesForExchange[1]);
//
//        if (exchangeRate == null) {
//            response.setStatus(404);
//            errorQuery = new ErrorQuery("Exchange rate not found - 404");
//            getJsonResponse(errorQuery, response);
//        } else {
//
//            currencyDAO.patchExchangeRate(baseCurrency, targetCurrency, rate);
//
//            try {
//                exchangeRate = currencyDAO.getExchangeRateByCode(currenciesForExchange[0], currenciesForExchange[1]);
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

}
