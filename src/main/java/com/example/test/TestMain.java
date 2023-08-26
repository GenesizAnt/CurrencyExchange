package com.example.test;

import com.example.data.ExchangeRateDAO;
import com.example.entity.Currency;
import java.sql.SQLException;
import java.util.ArrayList;


public class TestMain {
    public static void main(String[] args) throws SQLException {
        ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO();
        ConnectionPool connectionPool = CurrencyBDConnectionPool.create();
        exchangeRateDAO.setConnectionPool(connectionPool);
        try {
            ArrayList<Currency> exchangeRateByCode = exchangeRateDAO.getExchangeRateByCode("USD", "RUB");
            System.out.println(exchangeRateByCode.get(0));
        } catch (SQLException e) {
            throw new RuntimeException(e);
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


    }


}
