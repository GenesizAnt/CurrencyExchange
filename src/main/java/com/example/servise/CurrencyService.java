package com.example.servise;

import com.example.dto.CurrencyDTO;
import com.example.entity.Currency;
import com.example.test.CurrencyDAO;
import com.example.test.CurrencyMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CurrencyService {

    private final CurrencyDAO currencyDAO;
    private final CurrencyMapper currencyMapper;

    public CurrencyService() {
        this.currencyDAO = new CurrencyDAO();
        this.currencyMapper = new CurrencyMapper();
    }

    public CurrencyDTO findByCode(String code) {
        Currency currency = currencyDAO.getCurrencyByCode(code);
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

//    public Optional<List<CurrencyDTO>> findAllCurrencies() {
//
//
////        Optional<List<Currency>> currencies = currencyRepository.findAll();
////        if(currencies.isPresent()) {
////            List<CurrencyResDTO> list = currencyMapper.toDtoList(currencies.get());
////            return Optional.of(list);
////        }
//        return Optional.empty();
//    }

    public Optional<List<CurrencyDTO>> getAllCurrency() { //ToDo изучить как работает этот код или придумать другой способ
        Optional<List<Currency>> currencyList = currencyDAO.getAllCurrency();
        if (currencyList.isPresent()) {
            List<CurrencyDTO> list = currencyMapper.toDtoList(currencyList.get());
            return Optional.of(list);
        }
        return Optional.empty();
    }
}
