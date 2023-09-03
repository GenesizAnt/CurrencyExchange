package com.example.test;

import com.example.data.CurrencyDbDAO;
import com.example.dto.CurrencyDTO;
import com.example.entity.Currency;

import java.sql.SQLException;
import java.util.Optional;

public class CurrencyServiceTest {

    private final CurrencyDbDAO currencyDbDAO;
    private final CurrencyMapper currencyMapper;

    public CurrencyServiceTest() throws SQLException {
        this.currencyDbDAO = new CurrencyDbDAO();
        this.currencyMapper = new CurrencyMapper();
    }

    public CurrencyDTO findByCode(String code) {
        Optional<Currency> currency = currencyDbDAO.getCurrencyByCode(code);
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
