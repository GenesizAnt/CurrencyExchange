package com.example.currencyexchange;

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
            currencyDB.connect();

            currencyDB.getStatement().executeUpdate(getByCode);

            currencyDB.disconnect();
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Currency getCurrencyByCode(String code) {

        String getByCode = "SELECT * FROM currencies WHERE code='" + code + "'";

        try {
            currencyDB.connect();
            ResultSet resultSet = currencyDB.getStatement().executeQuery(getByCode);
            Currency currency = new Currency();

            currency.setId(resultSet.getInt("id"));
            currency.setCode(resultSet.getString("code"));
            currency.setFullName(resultSet.getString("fullName"));
            currency.setSign(resultSet.getString("sign"));

            currencyDB.disconnect();
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
            currencyDB.connect();
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
        currencyDB.disconnect();
        return currencyList;
    }

    public ArrayList<ExchangeRates> getAllExchangeRates() {

        //https://sysout.ru/sqlresultsetmapping-prevrashhenie-rezultata-sql-zaprosa-v-obekt/

//        String getAllRates = "SELECT * FROM exchangeRates";

        String getAllRates = "SELECT exchangeRates.id, base.Code AS Base, target.Code AS Target, exchangeRates.rate\n" +
                "FROM exchangeRates\n" +
                "INNER JOIN currencies base ON exchangeRates.BaseCurrencyId = base.ID\n" +
                "INNER JOIN currencies target ON exchangeRates.TargetCurrencyId = target.ID\n";


        ArrayList<ExchangeRates> exchangeRatesList = new ArrayList<>();

//        SELECT BaseCurrencyId, base.Code AS Base, TargetCurrencyId, target.Code AS Target, rate
//        FROM exchangeRates
//        INNER JOIN currencies base ON exchangeRates.BaseCurrencyId = base.ID
//        INNER JOIN currencies target ON exchangeRates.TargetCurrencyId = target.ID

        try {
            currencyDB.connect();
            ResultSet resultSet = currencyDB.getStatement().executeQuery(getAllRates);

            while (resultSet.next()) {
                ExchangeRates exchangeRates = new ExchangeRates();

                exchangeRates.setId(resultSet.getInt("id"));
                exchangeRates.setBaseCurrency(getCurrencyByCode(resultSet.getString("base")));
                exchangeRates.setTargetCurrency(getCurrencyByCode(resultSet.getString("target")));
                exchangeRates.setRate(resultSet.getDouble("rate"));

                exchangeRatesList.add(exchangeRates);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        currencyDB.disconnect();
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

    public PairCurrency getExchangeRate(String baseCurrencyId, String targetCurrencyId) {

        String getByExchangeRate = "SELECT exchangeRates.id, base.Code AS Base, target.Code AS Target, exchangeRates.rate\n" +
                "FROM exchangeRates\n" +
                "INNER JOIN currencies base ON exchangeRates.BaseCurrencyId = base.ID\n" +
                "INNER JOIN currencies target ON exchangeRates.TargetCurrencyId = target.ID\n" +
                "WHERE Base='" + baseCurrencyId + "' AND Target='" + targetCurrencyId + "'";

        try {
            currencyDB.connect();

            ResultSet resultSet = currencyDB.getStatement().executeQuery(getByExchangeRate);

            if (resultSet.isAfterLast()) {
                currencyDB.disconnect();
                return null;
            } else {
                ExchangeRates exchangeRates = new ExchangeRates();

                exchangeRates.setId(resultSet.getInt("ID"));
                exchangeRates.setBaseCurrency(getCurrencyByCode(baseCurrencyId));
                exchangeRates.setTargetCurrency(getCurrencyByCode(targetCurrencyId));
                exchangeRates.setRate(resultSet.getDouble("rate"));

//                int id = resultSet.getInt("id");
//                String baseCurrencyId1 = resultSet.getString("baseCurrencyId");
//                String targetCurrencyId1 = resultSet.getString("targetCurrencyId");
//                double rate = resultSet.getDouble("rate");
//
//                Currency baseCurrencyId = getCurrencyByCode(baseCurrencyId1);
//                Currency targetCurrencyId = getCurrencyByCode(targetCurrencyId1);
//
//                exchangeRates.setId(id);
//                exchangeRates.setBaseCurrency(baseCurrencyId);
//                exchangeRates.setTargetCurrency(targetCurrencyId);
//                exchangeRates.setRate(rate);

                currencyDB.disconnect();
                return exchangeRates;
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<PairCurrency> getExchangeThroughTransaction(String from, String to) {


        String getByExchangeRate = "SELECT exchangeRates.id, base.Code AS Base, target.Code AS Target, exchangeRates.rate\n" +
                "FROM exchangeRates\n" +
                "INNER JOIN currencies base ON exchangeRates.BaseCurrencyId = base.ID\n" +
                "INNER JOIN currencies target ON exchangeRates.TargetCurrencyId = target.ID\n" +
                "WHERE Base='USD' AND Target='" + from + "' OR Base='USD' AND Target='" + to + "'";

        try {
            currencyDB.connect();

            ResultSet resultSet = currencyDB.getStatement().executeQuery(getByExchangeRate);
            ArrayList<PairCurrency> pairCurrencies = new ArrayList<>();

            if (resultSet.isAfterLast()) {
                currencyDB.disconnect();
                return null;
            } else {
                ExchangeRates exchangeRates = new ExchangeRates();

                exchangeRates.setId(resultSet.getInt("id"));
                exchangeRates.setBaseCurrency(getCurrencyByCode(resultSet.getString("base")));
                exchangeRates.setTargetCurrency(getCurrencyByCode(resultSet.getString("target")));
                exchangeRates.setRate(resultSet.getDouble("rate"));

                pairCurrencies.add(exchangeRates);

                currencyDB.disconnect();
                return pairCurrencies;
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
