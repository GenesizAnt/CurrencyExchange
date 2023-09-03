package com.example.servise;

import com.example.error.CurrencyNotFoundException;
import com.example.dto.CurrencyDTO;
import com.example.entity.Currency;
import com.example.error.DatabaseException;
import com.example.data.CurrencyDAO;
import com.example.test.CurrencyMapper;

import java.util.List;
import java.util.Optional;

public class CurrencyService {

    private final CurrencyDAO currencyDAO;
    private final CurrencyMapper currencyMapper;
//    private final ExchangeRateMapper exchangeRateMapper;

    public CurrencyService() {
        this.currencyDAO = new CurrencyDAO();
        this.currencyMapper = new CurrencyMapper();
//        this.exchangeRateMapper = new ExchangeRateMapper();
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


        try {
            Optional<Currency> currency = currencyDAO.getCurrencyByCode(code);
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

    public Optional<List<CurrencyDTO>> getAllCurrency() throws CurrencyNotFoundException {
//        Optional<List<Currency>> currencyList = currencyDAO.getAllCurrency();
//        if (currencyList.isPresent()) {
//            List<CurrencyDTO> currencyDTOList = currencyMapper.toDtoList(currencyList.get());
//            return Optional.of(currencyDTOList);
//        }
//        return Optional.empty();


        try {
            Optional<List<Currency>> currencyList = currencyDAO.getAllCurrency();
            if (currencyList.isPresent()) {
                List<CurrencyDTO> currencyDTOList = currencyMapper.toDtoList(currencyList.get());
                return Optional.of(currencyDTOList);
            } else {
//                return Optional.empty();
                throw new CurrencyNotFoundException("Currency not found - 404");
            }
        } catch (CurrencyNotFoundException e) {
            throw new CurrencyNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new DatabaseException("Database is unavailable - 500");
        }
    }

    public void insertCurrency(String code, String name, String sign) {
        try {
            currencyDAO.insertCurrency(code, name, sign);
        } catch (DatabaseException e) {
            throw new DatabaseException("Currency with this code already exists - 409");
        } catch (RuntimeException e) {
            throw new RuntimeException("Database is unavailable - 500");
        }
    }

    public Optional<CurrencyDTO> getCurrencyById(Integer id) throws CurrencyNotFoundException {
//        Optional<Currency> currency = currencyDbDAO.getCurrencyById(id);
//        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);
//        return Optional.ofNullable(currencyDTO);

        try {
            Optional<Currency> currency = currencyDAO.getCurrencyById(id);
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
//        return null;
    }

//    public List<CurrencyDTO> getById(Integer id, int id2) {
////        Optional<Currency> currency = currencyDbDAO.getCurrencyById(id);
////        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);
////        return Optional.ofNullable(currencyDTO);
//
//        Optional<List<Currency>> currencyList = currencyDbDAO.getAllById(id, id2);
//            List<CurrencyDTO> currencyDTOList = currencyMapper.toDtoList(currencyList.get());
//            return currencyDTOList;
//
////            try {
////                Optional<List<Currency>> currencyList = currencyDbDAO.getAllById(id, id2);
////                if (currencyList.isPresent()) {
////                    List<CurrencyDTO> currencyDTOList = currencyMapper.toDtoList(currencyList.get());
////                    return currencyDTOList;
////                } else {
//////                return Optional.empty();
////                    throw new CurrencyNotFoundException("Currency not found - 404");
////                }
////            } catch (CurrencyNotFoundException e) {
//////            throw new CurrencyNotFoundException(e.getMessage());
////            } catch (Exception e) {
////                throw new DatabaseException("Database is unavailable - 500");
////            }
////            return null;
////        }
//    }
}