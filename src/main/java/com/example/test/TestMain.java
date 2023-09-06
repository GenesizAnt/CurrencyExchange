package com.example.test;

import com.example.entity.Currency;
import com.example.error.ValidationException;

import java.sql.SQLException;


public class TestMain {
    public static void main(String[] args) throws SQLException {

        String empty = "";
        String rate = "91.3544455";

        if (!(rate.equals(empty))) {
            if (rate.matches("\\d*[.]?\\d{1,6}\\b")) {
                if (!(rate.matches("[a-zA-Zа-яА-Я]+"))) {
                    System.out.println();
                }
            }
        }


//        ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO();
//        ConnectionPool connectionPool = CurrencyBDConnectionPool.create();
//        exchangeRateDAO.setConnectionPool(connectionPool);
//        try {
//            ArrayList<Currency> exchangeRateByCode = exchangeRateDAO.getExchangeRateByCode("USD", "RUB");
//            System.out.println(exchangeRateByCode.get(0));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//        Currency currency = new Currency(0, "USD", "Dollar", "$");
//        CurrencyMapper currencyMapper = new CurrencyMapper();
//        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);
//        currencyDTO.getName();

//        CurrencyService service = new CurrencyService();
//        String code = "USD";
//        CurrencyDTO currencyDTO = service.findByCode(code);
//        currencyDTO.getName();

//
//        CurrencyDAO currencyDAO = new CurrencyDAO();
//        Currency usd = currencyDAO.getCurrencyByCode("USD");
//        usd.getFullName();

    }
}


//        ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO();
//        init();
//        exchangeRateDAO.setConnectionBuilder(new PoolConnectionBuilder());
//        try {
//            ArrayList<Currency> exchangeRateByCode = exchangeRateDAO.getExchangeRateByCode("USD", "RUB");
//            System.out.println(exchangeRateByCode.get(0));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
