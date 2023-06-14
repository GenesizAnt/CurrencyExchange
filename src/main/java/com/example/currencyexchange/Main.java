package com.example.currencyexchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        Currency currency = new Currency(1, "RUB", "ryble", "R");

        String currencyJSON ="{\"id\":1,\"code\":\"RUB\",\"fullName\":\"ryble\",\"sign\":\"R\"}";

        ObjectMapper objectMapper = new ObjectMapper();//ToDo сделать красивый JSON https://habr.com/ru/companies/otus/articles/687004/

//        System.out.println(currency);

        try {
            Currency currency1 = objectMapper.readValue(currencyJSON, Currency.class);
            System.out.println(currency1);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(currencyJSON);

//        try {
//            String json = objectMapper.writeValueAsString(currency);
//            System.out.println(json);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }

        CurrencyDB currencyDB = new CurrencyDB();

//        String getAllCommand = "SELECT * FROM currency";
//
//        ResultSet resultSet = null;
//        try {
//
//            resultSet = currencyDB.getStatement().executeQuery(getAllCommand);
//
//
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }




//        try {
//            currencyDB.getStatement().executeUpdate("INSERT INTO currencies (code, fullName, sign) VALUES ('RUB', 'Russian Ruble', '₽')");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            currencyDB.disconnect();
//        }

//        try {
//            currencyDB.getStatement().executeUpdate("INSERT INTO exchangeRates (baseCurrencyId, targetCurrencyId, rate) VALUES ('RUB', 'USD', '0.012')");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            currencyDB.disconnect();
//        }

        try {
            double amount = 10000;
            double rate = 0;
            ResultSet resultSet = currencyDB.getStatement().executeQuery(
                    "SELECT valute1.code AS baseCode, valute2.code AS targetCode, rate " +
                            "FROM exchangeRates " +
                            "JOIN currencies AS valute1 ON valute1.code = exchangeRates.BaseCurrencyId " +
                            "JOIN currencies AS valute2 ON valute2.code = exchangeRates.TargetCurrencyId " +
                            "WHERE BaseCurrencyId='USD' AND TargetCurrencyId='RUB' " +
                            "OR BaseCurrencyId='RUB' AND TargetCurrencyId='USD' " +
                            "LIMIT 1");
            if (resultSet.getString("baseCode").equals("USD")) {
                rate = resultSet.getDouble("rate");
                System.out.println(amount / rate);
            } else {
                rate = resultSet.getDouble("rate");
                System.out.println(amount * rate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            currencyDB.disconnect();
        }

    }
}


//    SELECT valute1.*, valute2.*, rate
//        FROM exchangeRates
//        JOIN currencies AS valute1 ON valute1.code = exchangeRates.BaseCurrencyId
//        JOIN currencies AS valute2 ON valute2.code = exchangeRates.TargetCurrencyId
//        WHERE BaseCurrencyId='USD' AND TargetCurrencyId='RUB'
//        OR BaseCurrencyId='RUB' AND TargetCurrencyId='USD'
//        LIMIT 5