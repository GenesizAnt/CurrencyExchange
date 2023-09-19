package com.example.controller;

import com.example.data.ExchangeTransaction;
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
import java.math.BigDecimal;
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

            Map<String, String> requestParameter = checkRequestParameterForCurrency(request);

            try {
                currencyService.insertCurrency(requestParameter.get("code"),
                        requestParameter.get("name"),
                        requestParameter.get("sign"));

                Optional<CurrencyDTO> currencyDTO = currencyService.getCurrencyByCode(requestParameter.get("code"));
                getJsonResponse(response, 200, currencyDTO.get());

            } catch (DatabaseException e) {
                getJsonResponse(response, 409, e.getMessage());
            } catch (RuntimeException e) {
                getJsonResponse(response, 500, e.getMessage());
            } catch (CurrencyNotFoundException e) {
                getJsonResponse(response, 404, e.getMessage());
            }
        } catch (ValidationException e) {
            getJsonResponse(response, 400, e.getMessage());
        }
    }

    public void getAllExchangeRates(HttpServletResponse response) throws IOException {
        try {
            Optional<List<ExchangeRateDTO>> exchangeRateDTOList = exchangeRateService.getAllExchangeRates();
            if (exchangeRateDTOList.isPresent()) {
                getJsonResponse(response, 200, exchangeRateDTOList.get());
            } else {
                getJsonResponse(response, 404, new ExchangeRateNotFoundException("Exchange transaction rate not found - 404"));
            }
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

                if (exchangeRateDTO.isPresent()) {
                    getJsonResponse(response, 200, exchangeRateDTO.get());
                } else {
                    getJsonResponse(response, 404, new ExchangeRateNotFoundException("Exchange transaction rate not found - 404"));
                }

            } catch (DatabaseException e) {
                getJsonResponse(response, 500, e.getMessage());
            }
        } catch (ValidationException e) {
            getJsonResponse(response, 400, e.getMessage());
        }
    }

    public void postExchangeRate(HttpServletRequest request, HttpServletResponse response) throws IOException {


        try {

            Map<String, String> requestParameter = checkRequestParameterForSaveExchangeRate(request);
            Optional<ExchangeRateDTO> saveExchangeRateDTO = exchangeRateService.getExchangeRateForSave(requestParameter);

            if (exchangeRateService.isExchangeRateExist(saveExchangeRateDTO.get())) {
                getJsonResponse(response, 409, "Exchange rate with this currency already exists - 409");
            } else {

                exchangeRateService.insertExchangeRate(saveExchangeRateDTO.get());
                try {
                    Optional<ExchangeRateDTO> checkExchangeRateDTO = exchangeRateService.getExchangeRateByCode(
                            saveExchangeRateDTO.get().getBaseCurrency().getCode(),
                            saveExchangeRateDTO.get().getTargetCurrency().getCode());

                    if (checkExchangeRateDTO.isPresent()) {
                        getJsonResponse(response, 200, checkExchangeRateDTO.get());
                    } else {
                        getJsonResponse(response, 404, new ExchangeRateNotFoundException("Exchange transaction rate not found - 404"));
                    }

                } catch (DatabaseException e) {
                    getJsonResponse(response, 500, e.getMessage());
                }
            }
        } catch (ValidationException e) {
            getJsonResponse(response, 400, e.getMessage());
        }
    }

    public void patchExchangeRate(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            Map<String, String> requestParameter = checkRequestParameterForPatchExchangeRate(request);
            String[] currenciesForExchange = getCurrenciesForExchange(requestParameter.get("exchangeRate"));
            Optional<ExchangeRateDTO> exchangeRateDTO = exchangeRateService.getExchangeRateByCode(currenciesForExchange[0],
                    currenciesForExchange[1]);

            if (exchangeRateDTO.isPresent()) {
                exchangeRateService.patchExchangeRate(exchangeRateDTO.get(), new BigDecimal(requestParameter.get("rate")));
            } else {
                getJsonResponse(response, 404, new ExchangeRateNotFoundException("Exchange transaction rate not found - 404"));
            }

            try {

                Optional<ExchangeRateDTO> checkExchangeRateDTO = exchangeRateService.getExchangeRateByCode(
                        currenciesForExchange[0],
                        currenciesForExchange[1]);

                if (checkExchangeRateDTO.isPresent()) {
                    getJsonResponse(response, 200, checkExchangeRateDTO.get());
                } else {
                    getJsonResponse(response, 404, new ExchangeRateNotFoundException("Exchange transaction rate not found - 404"));
                }

            } catch (DatabaseException e) {
                getJsonResponse(response, 500, e.getMessage());
            }
        } catch (ValidationException e) {
            getJsonResponse(response, 400, e.getMessage());
        }
    }

    public void getExchangeTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            Map<String, String> requestParameter = checkRequestParameterForExchangeTransaction(request);

            Optional<CurrencyDTO> baseCurrencyCode = currencyService.getCurrencyByCode(requestParameter.get("baseCurrencyCode"));
            Optional<CurrencyDTO> targetCurrencyCode = currencyService.getCurrencyByCode(requestParameter.get("targetCurrencyCode"));
            BigDecimal amountForExchange = BigDecimal.valueOf(Long.parseLong(requestParameter.get("amount")));

            Optional<ExchangeRateDTO> exchangeDirectRate;
            Optional<ExchangeRateDTO> exchangeReverseRate;
            Optional<List<ExchangeRateDTO>> exchangesThroughUSDRate;

            try {
                exchangeDirectRate = exchangeRateService.getExchangeRateByCode(baseCurrencyCode.get().getCode(),
                        targetCurrencyCode.get().getCode());
                exchangeReverseRate = exchangeRateService.getExchangeRateByCode(targetCurrencyCode.get().getCode(),
                        baseCurrencyCode.get().getCode());
                exchangesThroughUSDRate = exchangeRateService.getExchangeThroughTransaction(baseCurrencyCode.get().getId(),
                        targetCurrencyCode.get().getId());

                if (exchangeDirectRate.isPresent()) {
                    ExchangeTransaction exchangeTransaction = new ExchangeTransaction(exchangeDirectRate.get());
                    exchangeTransaction.calculateExchangeTransaction(amountForExchange);
                    getJsonResponse(response, 200, exchangeTransaction);
                } else if (exchangeReverseRate.isPresent()) {
                    ExchangeTransaction exchangeTransaction = new ExchangeTransaction(exchangeReverseRate.get().getTargetCurrency(),
                            exchangeReverseRate.get().getBaseCurrency(),
                            exchangeReverseRate.get().getRate());
                    exchangeTransaction.calculateReverseExchangeTransaction(amountForExchange);
                    getJsonResponse(response, 200, exchangeTransaction);
                } else if (exchangesThroughUSDRate.isPresent()) {
                    ExchangeTransaction exchangeTransaction = new ExchangeTransaction(exchangesThroughUSDRate.get().get(0),
                            exchangesThroughUSDRate.get().get(1));
                    exchangeTransaction.calculateExchangeTransactionThroughUSD(amountForExchange, exchangeTransaction.getRate());
                    getJsonResponse(response, 200, exchangeTransaction);
                } else {
                    getJsonResponse(response, 404, new ExchangeRateNotFoundException("Exchange transaction rate not found - 404").getMessage());
                }

            } catch (DatabaseException e) {
                getJsonResponse(response, 500, e.getMessage());
            }
        } catch (ValidationException e) {
            getJsonResponse(response, 400, e.getMessage());
        } catch (CurrencyNotFoundException e) {
            getJsonResponse(response, 404, e.getMessage());
        }
    }
}
