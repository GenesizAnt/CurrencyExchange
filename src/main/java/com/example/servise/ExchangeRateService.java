package com.example.servise;

import com.example.data.dao.ExchangeRateDAO;
import com.example.dto.ExchangeRateDTO;
import com.example.entity.ExchangeRate;
import com.example.error.DatabaseException;
import com.example.dto.mapper.ExchangeRateMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ExchangeRateService {

    private final ExchangeRateDAO exchangeRateDAO;
    private final ExchangeRateMapper exchangeRateMapper;

    public ExchangeRateService() {
        this.exchangeRateDAO = new ExchangeRateDAO();
        this.exchangeRateMapper = new ExchangeRateMapper();
    }

    public Optional<List<ExchangeRateDTO>> getAllExchangeRates() {
        try {
            Optional<List<ExchangeRate>> exchangeRateList = exchangeRateDAO.getAllExchangeRates();
            if (exchangeRateList.isPresent()) {
                List<ExchangeRateDTO> exchangeRateDTOList = exchangeRateMapper.toDtoList(exchangeRateList.get());
                return Optional.of(exchangeRateDTOList);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new DatabaseException("Database is unavailable - 500");
        }
    }

    public Optional<ExchangeRateDTO> getExchangeRateByCode(String baseCurrencyCode, String targetCurrencyCode) {
        try {
            Optional<ExchangeRate> exchangeRate = exchangeRateDAO.getExchangeRateCode(baseCurrencyCode, targetCurrencyCode);
            if (exchangeRate.isPresent()) {
                ExchangeRateDTO exchangeRateDTO = exchangeRateMapper.toDto(exchangeRate.get());
                return Optional.ofNullable(exchangeRateDTO);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new DatabaseException("Database is unavailable - 500");
        }
    }

    public void saveExchangeRate(ExchangeRateDTO exchangeRateDTO) {
        try {
            exchangeRateDAO.saveExchangeRate(exchangeRateDTO.getBaseCurrency().getId(),
                                               exchangeRateDTO.getTargetCurrency().getId(),
                                               exchangeRateDTO.getRate());
        } catch (Exception e) {
            throw new RuntimeException("Database is unavailable - 500");
        }
    }

    public Optional<ExchangeRateDTO> getExchangeRateForSave(Map<String, String> requestParameter) {
        ExchangeRateDTO exchangeRateDTO = exchangeRateMapper.toModel(requestParameter);
        return Optional.ofNullable(exchangeRateDTO);
    }

    public boolean isExchangeRateExist(ExchangeRateDTO saveExchangeRateDTO) {
        Optional<ExchangeRate> exchangeRate = exchangeRateDAO.getExchangeRateCode(
                                                                     saveExchangeRateDTO.getBaseCurrency().getCode(),
                                                                     saveExchangeRateDTO.getTargetCurrency().getCode());
        return exchangeRate.isPresent();
    }

    public void updateExchangeRate(ExchangeRateDTO exchangeRateDTO, BigDecimal rate) {
        try {
            exchangeRateDAO.updateExchangeRate(exchangeRateDTO, rate);
        } catch (Exception e) {
            throw new RuntimeException("Database is unavailable - 500");
        }
    }

    public Optional<List<ExchangeRateDTO>> getExchangeThroughTransaction(int baseCurrencyCode, int targetCurrencyCode) {
        try {

            Optional<List<ExchangeRate>> exchangesThroughUSDRate = exchangeRateDAO.getExchangeThroughTransaction(baseCurrencyCode, targetCurrencyCode);

            if (exchangesThroughUSDRate.isPresent()) {
                List<ExchangeRateDTO> exchangeRateDTOList = exchangeRateMapper.toDtoList(exchangesThroughUSDRate.get());
                return Optional.of(exchangeRateDTOList);
            } else {
                return Optional.empty();
            }

        } catch (Exception e) {
            throw new DatabaseException("Database is unavailable - 500");
        }
    }
}
