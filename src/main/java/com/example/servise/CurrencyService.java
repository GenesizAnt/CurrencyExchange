package com.example.servise;

import com.example.dto.CurrencyDTO;
import com.example.entity.Currency;
import com.example.error.DatabaseException;
import com.example.data.CurrencyDAO;
import com.example.dto.CurrencyMapper;

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
            Optional<List<Currency>> currencyList = currencyDAO.getAllCurrency();
            if (currencyList.isPresent()) {
                List<CurrencyDTO> currencyDTOList = currencyMapper.toDtoList(currencyList.get());
                return Optional.of(currencyDTOList);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new DatabaseException("Database is unavailable - 500");
        }
    }

    public void insertCurrency(String code, String name, String sign) {
        try {
            currencyDAO.insertCurrency(code, name, sign);
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