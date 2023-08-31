package com.example.servise;

import com.example.error.CurrencyNotFoundException;
import com.example.dto.CurrencyDTO;
import com.example.entity.Currency;
import com.example.error.DatabaseException;
import com.example.test.CurrencyDAO;
import com.example.test.CurrencyMapper;

import java.util.List;
import java.util.Optional;

public class CurrencyService {

    private final CurrencyDAO currencyDAO;
    private final CurrencyMapper currencyMapper;

    public CurrencyService() {
        this.currencyDAO = new CurrencyDAO();
        this.currencyMapper = new CurrencyMapper();
    }

    public Optional<CurrencyDTO> getCurrencyByCode(String code) throws CurrencyNotFoundException {
//        Currency currency = currencyDAO.getCurrencyByCode(code);
//        if (currency != null) {
//            CurrencyDTO currencyDTO = currencyMapper.toDto(currency);
//            return Optional.ofNullable(currencyDTO);
//        }
//
////        if(currency.isPresent()) {
////            CurrencyResDTO currencyResDTO = currencyMapper.toDTO(currency.get());
////            return Optional.of(currencyResDTO);
////        }
////        return Optional.empty();
//
//        return null; // return Optional.empty();


        Optional<Currency> currency;
        try {
            currency = currencyDAO.getCurrencyByCode(code);
            if (currency.isPresent()) {
                CurrencyDTO currencyDTO = currencyMapper.toDto(currency);
                return Optional.ofNullable(currencyDTO);
            } else {
                throw new CurrencyNotFoundException("Currency not found - 404");
            }
        } catch (CurrencyNotFoundException e) {
            throw new CurrencyNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new DatabaseException("Database is unavailable - 500");
        }


//        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);
//        return Optional.ofNullable(currencyDTO);



//        try {
//            Currency currency = currencyDAO.getCurrencyByCode(code);
//            CurrencyDTO currencyDTO = currencyMapper.toDto(currency);
//            return Optional.ofNullable(currencyDTO);
//        } catch (Exception e) {
//            throw new CurrencyNotFoundException();
//        }

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
            List<CurrencyDTO> currencyDTOList = currencyMapper.toDtoList(currencyList.get());
            return Optional.of(currencyDTOList);
        }
        return Optional.empty();
    }
}
