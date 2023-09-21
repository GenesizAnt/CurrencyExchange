package com.example.controller;

import com.example.dto.ExchangeTransactionDTO;
import com.example.dto.ExchangeRateDTO;
import com.example.error.*;
import com.example.dto.CurrencyDTO;
import com.example.servise.CurrencyService;
import com.example.servise.ExchangeRateService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.Util.*;

public class RequestValidation extends HttpServlet {

    private final CurrencyService currencyService = new CurrencyService();
    private final ExchangeRateService exchangeRateService = new ExchangeRateService();

    public void getCurrency(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            isCorrectCodeCurrency(request);
            String currencyCode = getCodeFromURL(request);
            try {

                Optional<CurrencyDTO> currencyDTO = currencyService.getCurrencyByCode(currencyCode);

                if (currencyDTO.isPresent()) {
                    getJsonResponse(response, 200, currencyDTO.get());
                } else {
                    getJsonResponse(response, 404, new ExchangeRateNotFoundException("Currency not found - 404").getMessage());
                }
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
            if (allCurrency.isPresent()) {
                getJsonResponse(response, 200, allCurrency.get());
            } else {
                getJsonResponse(response, 404, new ExchangeRateNotFoundException("Currency not found - 404").getMessage());
            }
        } catch (DatabaseException e) {
            getJsonResponse(response, 500, e.getMessage());
        }
    }

    public void saveNewCurrency(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            Map<String, String> requestParameter = checkRequestParameterForCurrency(request);

            try {
                currencyService.saveNewCurrency(requestParameter.get("code"),
                        requestParameter.get("name"),
                        requestParameter.get("sign"));

                Optional<CurrencyDTO> currencyDTO = currencyService.getCurrencyByCode(requestParameter.get("code"));
                if (currencyDTO.isPresent()) {
                    getJsonResponse(response, 200, currencyDTO.get());
                } else {
                    getJsonResponse(response, 404, new ExchangeRateNotFoundException("Currency not found - 404").getMessage());
                }

            } catch (DatabaseException e) {
                getJsonResponse(response, 409, e.getMessage());
            } catch (RuntimeException e) {
                getJsonResponse(response, 500, e.getMessage());
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

    public void saveExchangeRate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            Map<String, String> requestParameter = checkRequestParameterForSaveExchangeRate(request);
            Optional<ExchangeRateDTO> exchangeRateForSave = exchangeRateService.getExchangeRateForSave(requestParameter);

            if (exchangeRateService.isExchangeRateExist(exchangeRateForSave.get())) {
                getJsonResponse(response, 409, "Exchange rate with this currency already exists - 409");
            } else {

                exchangeRateService.saveExchangeRate(exchangeRateForSave.get());
                try {
                    Optional<ExchangeRateDTO> checkExchangeRateDTO = exchangeRateService.getExchangeRateByCode(
                            exchangeRateForSave.get().getBaseCurrency().getCode(),
                            exchangeRateForSave.get().getTargetCurrency().getCode());

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

    public void updateExchangeRate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            Map<String, String> requestParameter = checkRequestParameterForPatchExchangeRate(request);
            String[] currenciesForExchange = getCurrenciesForExchange(requestParameter.get("exchangeRate"));
            Optional<ExchangeRateDTO> exchangeRateDTO = exchangeRateService.getExchangeRateByCode(currenciesForExchange[0],
                    currenciesForExchange[1]);

            if (exchangeRateDTO.isPresent()) {
                exchangeRateService.updateExchangeRate(exchangeRateDTO.get(), new BigDecimal(requestParameter.get("rate")));
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
            Optional<List<ExchangeRateDTO>> exchangeThroughUSDRate;

            try {
                exchangeDirectRate = exchangeRateService.getExchangeRateByCode(baseCurrencyCode.get().getCode(),
                        targetCurrencyCode.get().getCode());
                exchangeReverseRate = exchangeRateService.getExchangeRateByCode(targetCurrencyCode.get().getCode(),
                        baseCurrencyCode.get().getCode());
                exchangeThroughUSDRate = exchangeRateService.getExchangeThroughTransaction(baseCurrencyCode.get().getId(),
                        targetCurrencyCode.get().getId());

                if (exchangeDirectRate.isPresent()) {
                    ExchangeTransactionDTO exchangeTransactionDTO = new ExchangeTransactionDTO(exchangeDirectRate.get());
                    exchangeTransactionDTO.calculateExchangeTransaction(amountForExchange);
                    getJsonResponse(response, 200, exchangeTransactionDTO);
                } else if (exchangeReverseRate.isPresent()) {
                    ExchangeTransactionDTO exchangeTransactionDTO = new ExchangeTransactionDTO(exchangeReverseRate.get().getTargetCurrency(),
                            exchangeReverseRate.get().getBaseCurrency(),
                            exchangeReverseRate.get().getRate());
                    exchangeTransactionDTO.calculateReverseExchangeTransaction(amountForExchange);
                    getJsonResponse(response, 200, exchangeTransactionDTO);
                } else if (exchangeThroughUSDRate.isPresent()) {
                    ExchangeTransactionDTO exchangeTransactionDTO = new ExchangeTransactionDTO(exchangeThroughUSDRate.get().get(0),
                            exchangeThroughUSDRate.get().get(1));
                    exchangeTransactionDTO.calculateExchangeTransactionThroughUSD(amountForExchange, exchangeTransactionDTO.getRate());
                    getJsonResponse(response, 200, exchangeTransactionDTO);
                } else {
                    getJsonResponse(response, 404, new ExchangeRateNotFoundException("Exchange transaction rate not found - 404").getMessage());
                }

            } catch (DatabaseException e) {
                getJsonResponse(response, 500, e.getMessage());
            }
        } catch (ValidationException e) {
            getJsonResponse(response, 400, e.getMessage());
        }
    }
}
