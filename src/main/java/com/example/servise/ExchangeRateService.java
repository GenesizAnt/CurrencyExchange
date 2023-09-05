package com.example.servise;

//import com.example.data.ExchangeRateDAO;

import com.example.data.ExchangeRateDAO;
import com.example.dto.ExchangeRateDTO;
import com.example.entity.ExchangeRate;
import com.example.error.DatabaseException;
import com.example.error.ExchangeRateNotFoundException;
import com.example.test.ExchangeRateMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ExchangeRateService {

    private final ExchangeRateDAO exchangeRateDAO;
    //    private final CurrencyDbDAO currencyDbDAO;
    private final ExchangeRateMapper exchangeRateMapper;

    public ExchangeRateService() {
        this.exchangeRateDAO = new ExchangeRateDAO();
        this.exchangeRateMapper = new ExchangeRateMapper();
    }

    public Optional<List<ExchangeRateDTO>> getAllExchangeRates() throws ExchangeRateNotFoundException {
//        Optional<List<ExchangeRate>> exchangeRateList = currencyDbDAO.getAllExchangeRates();
//        List<ExchangeRateDTO> exchangeRateDTOList = exchangeRateMapper.toDtoList(exchangeRateList.get());
//        return Optional.of(exchangeRateDTOList);

        try {
            Optional<List<ExchangeRate>> exchangeRateList = exchangeRateDAO.getAllExchangeRates();
            if (exchangeRateList.isPresent()) {
                List<ExchangeRateDTO> exchangeRateDTOList = exchangeRateMapper.toDtoList(exchangeRateList.get());
                return Optional.of(exchangeRateDTOList);
            } else {
                throw new ExchangeRateNotFoundException("ExchangeRate not found - 404");
            }
        } catch (ExchangeRateNotFoundException e) {
            throw new ExchangeRateNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new DatabaseException("Database is unavailable - 500");
        }
    }

    public Optional<ExchangeRateDTO> getExchangeRateByCode(String baseCurrencyCode, String targetCurrencyCode) throws ExchangeRateNotFoundException {

        try {
            Optional<ExchangeRate> exchangeRate = exchangeRateDAO.getExchangeRateCode(baseCurrencyCode, targetCurrencyCode);
            if (exchangeRate.isPresent()) {
                ExchangeRateDTO exchangeRateDTO = exchangeRateMapper.toDto(exchangeRate.get());
                return Optional.ofNullable(exchangeRateDTO);
            } else {
                throw new ExchangeRateNotFoundException("ExchangeRate not found - 404");
            }
        } catch (ExchangeRateNotFoundException e) {
            throw new ExchangeRateNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new DatabaseException("Database is unavailable - 500");
        }
    }

    public void insertExchangeRate(ExchangeRateDTO exchangeRateDTO) {
        try {
//            exchangeRateDAO.insertExchangeRate(baseCurrencyId, targetCurrencyId, rate);
            exchangeRateDAO.insertExchangeRate(exchangeRateDTO.getBaseCurrency().getId(),
                                               exchangeRateDTO.getTargetCurrency().getId(),
                                               exchangeRateDTO.getRate());
        } catch (RuntimeException e) {
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

//    public void insertExchangeRate(Optional<ExchangeRateDTO> exchangeRateDTO) {
//
//    }
}
