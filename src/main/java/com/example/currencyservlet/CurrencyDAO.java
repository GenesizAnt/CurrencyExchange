package com.example.currencyservlet;

import com.example.currencyexchange.Currency;
import com.example.currencyexchange.CurrencyDB;
import com.example.currencyexchange.ExchangeRates;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CurrencyDAO {

    private CurrencyDB currencyDB;

    public CurrencyDAO() {
        this.currencyDB = new CurrencyDB();
    }

    public void postCurrency(String codeCurrency, String nameCurrency, String signCurrency) {

//        String d = "{\"id\":1,\"code\":\"EUR\",\"fullName\":\"Euro\",\"sign\":\"E\"}";

        String codeCurrency1 = codeCurrency;
        String nameCurrency1 = nameCurrency;
        String signCurrency1 = signCurrency;

        String getByCode = "INSERT INTO currencies (code, fullName, sign) VALUES ('" + codeCurrency1 + "', '" + nameCurrency1 + "', '" + signCurrency1 + "')";

        try {

            currencyDB.getStatement().executeUpdate(getByCode);

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Currency getCurrencyByCode(String code) {

        String getByCode = "SELECT * FROM currencies WHERE code='" + code + "'";

        try {
            ResultSet resultSet = currencyDB.getStatement().executeQuery(getByCode);
            Currency currency = new Currency();

            currency.setId(resultSet.getInt("id"));
            currency.setCode(resultSet.getString("code"));
            currency.setFullName(resultSet.getString("fullName"));
            currency.setSign(resultSet.getString("sign"));

            return currency;
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Currency> getAllCurrency() {

        String getAllCommand = "SELECT * FROM currencies";
        ArrayList<Currency> currencyList = new ArrayList<>();

        try {
            ResultSet resultSet = currencyDB.getStatement().executeQuery(getAllCommand);

            while (resultSet.next()) {
                Currency currency = new Currency();

                currency.setId(resultSet.getInt("id"));
                currency.setCode(resultSet.getString("code"));
                currency.setFullName(resultSet.getString("fullName"));
                currency.setSign(resultSet.getString("sign"));

                currencyList.add(currency);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currencyList;
    }

    public ArrayList<ExchangeRates> getExchangeRates() {

        //https://sysout.ru/sqlresultsetmapping-prevrashhenie-rezultata-sql-zaprosa-v-obekt/

        String getAllRates = "SELECT * FROM exchangeRates";
        ArrayList<ExchangeRates> exchangeRatesList = new ArrayList<>();

        try {
            ResultSet resultSet = currencyDB.getStatement().executeQuery(getAllRates);

            while (resultSet.next()) {
                ExchangeRates exchangeRates = new ExchangeRates();

                int id = resultSet.getInt("id");
                String baseCurrencyId1 = resultSet.getString("baseCurrencyId");
                String targetCurrencyId1 = resultSet.getString("targetCurrencyId");
                double rate = resultSet.getDouble("rate");

//                Currency baseCurrencyId = getCurrencyByCode(baseCurrencyId1);
//                Currency targetCurrencyId = getCurrencyByCode(targetCurrencyId1);

                exchangeRates.setId(id);
//                exchangeRates.setBaseCurrency(baseCurrencyId);
//                exchangeRates.setTargetCurrency(targetCurrencyId);
                exchangeRates.setRate(rate);

//                exchangeRates.setId(resultSet.getInt(1));
//                exchangeRates.setBaseCurrency(getCurrencyByCode(resultSet.getString(2)));
//                exchangeRates.setTargetCurrency(getCurrencyByCode(resultSet.getString(3)));
//                exchangeRates.setRate(resultSet.getDouble(4));

                exchangeRatesList.add(exchangeRates);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exchangeRatesList;
    }

    public boolean isCurrentCode(String codeCurrent) {

        String requestSQL = "SELECT EXISTS(SELECT 1 FROM currencies WHERE code='" + codeCurrent + "') AS is_exists";

        try {

            ResultSet resultSet = currencyDB.getStatement().executeQuery(requestSQL);

            int isCurrent = resultSet.getInt("is_exists");

            if (isCurrent == 1) {
                return true;
            } else {
                return false;
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAllExchangeRates() {
//        String getAllCommand = "SELECT * FROM exchangeRates";
//        ArrayList<Currency> currencyList = new ArrayList<>();
//
//        try {
//            ResultSet resultSet = currencyDB.getStatement().executeQuery(getAllCommand);
//
//            while (resultSet.next()) {
//                Currency currency = new Currency();
//
//                currency.setId(resultSet.getInt("id"));
//                currency.setCode(resultSet.getString("code"));
//                currency.setFullName(resultSet.getString("fullName"));
//                currency.setSign(resultSet.getString("sign"));
//
//                currencyList.add(currency);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return currencyList;
    }
}
