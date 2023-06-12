package com.example.currencyexchange;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        CurrencyDB currencyDB = new CurrencyDB();
//        try {
//            currencyDB.getStatement().executeUpdate("INSERT INTO currencies (code, fullName, sign) VALUES ('RUB', 'Russian Ruble', '₽')");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            currencyDB.disconnect();
//        }

        try {
            currencyDB.getStatement().executeUpdate("INSERT INTO exchangeRates (baseCurrencyId, targetCurrencyId, rate) VALUES ('USD', 'RUB', '83.15')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            currencyDB.disconnect();
        }

    }
}
