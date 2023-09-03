package com.example.servise;

//import com.example.data.ExchangeRateDAO;
import com.example.data.ExchangeRateDAO;
import com.example.dto.CurrencyDTO;
import com.example.dto.ExchangeRateDTO;
import com.example.entity.ExchangeRate;
import com.example.error.CurrencyNotFoundException;
import com.example.error.DatabaseException;
import com.example.error.ExchangeRateNotFoundException;
import com.example.test.ExchangeRateMapper;

import java.util.List;
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

    public Optional<ExchangeRateDTO> getExchangeRateByCode(String baseCurrencyCode, String targetCurrencyCode) {

        try {
            Optional<ExchangeRate> exchangeRate = exchangeRateDAO.getExchangeRateCode(baseCurrencyCode, targetCurrencyCode);
            if (exchangeRate.isPresent()) {
                ExchangeRateDTO exchangeRateDTO = exchangeRateMapper.toDto(exchangeRate.get()); //ToDo передавить Оптионал или нет??
                return Optional.ofNullable(exchangeRateDTO);
            } else {
                throw new CurrencyNotFoundException("Currency not found - 404");
            }
        } catch (CurrencyNotFoundException e) {
//            throw new CurrencyNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new DatabaseException("Database is unavailable - 500");
        }
        return null;
    }
}
