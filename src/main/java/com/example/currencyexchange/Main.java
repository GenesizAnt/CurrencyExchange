package com.example.currencyexchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static java.math.BigDecimal.ROUND_DOWN;

public class Main {
    public static void main(String[] args) {

        Currency currency = new Currency(1, "EUR", "Euro", "E");
        String getByCode = "INSERT INTO currencies (code, fullName, sign) VALUES ('EUR', 'Euro', 'E')";

        String q = "USDRUB";
        String w = q.substring(0, 3);
        String e = q.substring(3, 6);

        System.out.println(w + " " + e);

//        BigDecimal bigDecimal = BigDecimal.valueOf(2.15);
//        BigDecimal amount = BigDecimal.valueOf(10.00);
//        BigDecimal convertedAmount = amount.multiply(bigDecimal).setScale(2, RoundingMode.HALF_DOWN);
//        System.out.println(convertedAmount);

        Double bigDecimal = 2.15;
        Double amount = 10.00;
        Double convertedAmount = amount / bigDecimal;
        System.out.println(convertedAmount);


        int i = ThreadLocalRandom.current().nextInt(100);


        System.out.println(i);

//        String currencyJSON = "{\"id\":1,\"code\":\"RUB\",\"fullName\":\"ryble\",\"sign\":\"R\"}";
//
        ObjectMapper objectMapper = new ObjectMapper();//ToDo сделать красивый JSON https://habr.com/ru/companies/otus/articles/687004/
//
//        String originalString = "code=EUR fullName=Euro sign=E";
//        try {
//            String encodedString = URLEncoder.encode(originalString, "UTF-8");
//            System.out.println("Encoded String: " + encodedString);
//        } catch (UnsupportedEncodingException e) {
//            System.out.println("Unsupported Encoding: " + e.getMessage());
//        }

        String s = "http://localhost:8080/CurrencyExchange_war_exploded/router/exchange?from=BASE_CURRENCY_CODE&to=TARGET_CURRENCY_CODE&amount=$AMOUNT";
        String[] split = s.split("/");
        int d = 3;


        System.out.println(Arrays.toString(split));

//        try {
//            Currency currency1 = objectMapper.readValue(currencyJSON, Currency.class);
//            System.out.println(currency1);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(currencyJSON);
//
//        try {
//            String json = objectMapper.writeValueAsString(currency);
//            System.out.println(json);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }

//        CurrencyDB currencyDB = new CurrencyDB();

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

//        try {
//            double amount = 10000;
//            double rate = 0;
//            ResultSet resultSet = currencyDB.getStatement().executeQuery(
//                    "SELECT valute1.code AS baseCode, valute2.code AS targetCode, rate " +
//                            "FROM exchangeRates " +
//                            "JOIN currencies AS valute1 ON valute1.code = exchangeRates.BaseCurrencyId " +
//                            "JOIN currencies AS valute2 ON valute2.code = exchangeRates.TargetCurrencyId " +
//                            "WHERE BaseCurrencyId='USD' AND TargetCurrencyId='RUB' " +
//                            "OR BaseCurrencyId='RUB' AND TargetCurrencyId='USD' " +
//                            "LIMIT 1");
//            if (resultSet.getString("baseCode").equals("USD")) {
//                rate = resultSet.getDouble("rate");
//                System.out.println(amount / rate);
//            } else {
//                rate = resultSet.getDouble("rate");
//                System.out.println(amount * rate);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            currencyDB.disconnect();
//        }

    }
}


//    SELECT valute1.*, valute2.*, rate
//        FROM exchangeRates
//        JOIN currencies AS valute1 ON valute1.code = exchangeRates.BaseCurrencyId
//        JOIN currencies AS valute2 ON valute2.code = exchangeRates.TargetCurrencyId
//        WHERE BaseCurrencyId='USD' AND TargetCurrencyId='RUB'
//        OR BaseCurrencyId='RUB' AND TargetCurrencyId='USD'
//        LIMIT 5