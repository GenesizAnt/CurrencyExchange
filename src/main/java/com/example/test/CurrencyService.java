package com.example.test;

import com.example.entity.Currency;

import java.sql.SQLException;
import java.util.Optional;

public class CurrencyService {

    private final CurrencyDAOtest currencyDAOtest;
    private final CurrencyMapper currencyMapper;

    public CurrencyService() throws SQLException {
        this.currencyDAOtest = new CurrencyDAOtest();
        this.currencyMapper = new CurrencyMapper();
    }

    public CurrencyDTO findByCode(String code) {
        Currency currency = currencyDAOtest.getCurrencyByCode(code);
        if (currency != null) {
            CurrencyDTO currencyDTO = currencyMapper.toDto(currency);
            return currencyDTO;
        }

//        if(currency.isPresent()) {
//            CurrencyResDTO currencyResDTO = currencyMapper.toDTO(currency.get());
//            return Optional.of(currencyResDTO);
//        }
//        return Optional.empty();

        return null; // return Optional.empty();
    }
}
