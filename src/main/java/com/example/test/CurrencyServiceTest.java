package com.example.test;

import com.example.data.CurrencyDAO;
import com.example.dto.CurrencyDTO;
import com.example.entity.Currency;

import java.sql.SQLException;
import java.util.Optional;

public class CurrencyServiceTest {

    private final CurrencyDAO currencyDAO;
    private final CurrencyMapper currencyMapper;

    public CurrencyServiceTest() throws SQLException {
        this.currencyDAO = new CurrencyDAO();
        this.currencyMapper = new CurrencyMapper();
    }

    public CurrencyDTO findByCode(String code) {
        Optional<Currency> currency = currencyDAO.getCurrencyByCode(code);
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
