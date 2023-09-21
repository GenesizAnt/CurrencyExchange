package com.example.servise;

import com.example.dto.CurrencyDTO;
import com.example.entity.Currency;
import com.example.error.DatabaseException;
import com.example.data.dao.CurrencyDAO;
import com.example.dto.mapper.CurrencyMapper;

import java.util.List;
import java.util.Optional;

public class CurrencyService {

    private final CurrencyDAO currencyDAO;
    private final CurrencyMapper currencyMapper;

    public CurrencyService() {
        this.currencyDAO = new CurrencyDAO();
        this.currencyMapper = new CurrencyMapper();
    }

    public Optional<CurrencyDTO> getCurrencyByCode(String code) {
        try {
            Optional<Currency> currency = currencyDAO.getCurrencyByCode(code);
            if (currency.isPresent()) {
                CurrencyDTO currencyDTO = currencyMapper.toDto(currency);
                return Optional.ofNullable(currencyDTO);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new DatabaseException("Database is unavailable - 500");
        }
    }

    public Optional<List<CurrencyDTO>> getAllCurrency() {
        try {
            Optional<List<Currency>> allCurrency = currencyDAO.getAllCurrency();
            if (allCurrency.isPresent()) {
                List<CurrencyDTO> allCurrencyDTO = currencyMapper.toDtoList(allCurrency.get());
                return Optional.of(allCurrencyDTO);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new DatabaseException("Database is unavailable - 500");
        }
    }

    public void saveNewCurrency(String code, String name, String sign) {
        try {
            currencyDAO.saveNewCurrency(code, name, sign);
        } catch (DatabaseException e) {
            throw new DatabaseException(e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException("Database is unavailable - 500");
        }
    }

    public Optional<CurrencyDTO> getCurrencyById(Integer id) {
        try {
            Optional<Currency> currency = currencyDAO.getCurrencyById(id);
            if (currency.isPresent()) {
                CurrencyDTO currencyDTO = currencyMapper.toDto(currency);
                return Optional.ofNullable(currencyDTO);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new DatabaseException("Database is unavailable - 500");
        }
    }
}