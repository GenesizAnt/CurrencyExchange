package com.example.data;

import com.example.entity.Currency;
import com.example.entity.ExchangeRate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CurrencyDAO {

    private CurrencyDB currencyDB;

    public CurrencyDAO() {
        this.currencyDB = new CurrencyDB();
    }

    public void insertCurrency(String codeCurrency, String nameCurrency, String signCurrency) {
        String insertCurrencyCommand = "INSERT INTO currencies (code, fullName, sign) VALUES ('" + codeCurrency + "', '" + nameCurrency + "', '" + signCurrency + "')";
        currencyDB.connect();
        try {
            currencyDB.getStatement().executeUpdate(insertCurrencyCommand);
            currencyDB.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Currency getCurrencyByCode(String codeCurrency) {
        String getByCodeCommand = "SELECT * FROM currencies WHERE code='" + codeCurrency + "'";
        currencyDB.connect();
        try {
            ResultSet resultSet = currencyDB.getStatement().executeQuery(getByCodeCommand);
            if (!resultSet.next()) {
                resultSet.close();
                currencyDB.disconnect();
                return null;
            } else {
                Currency currency = new Currency();
                currency.setId(resultSet.getInt("id"));
                currency.setCode(resultSet.getString("code"));
                currency.setFullName(resultSet.getString("fullName"));
                currency.setSign(resultSet.getString("sign"));
                resultSet.close();
                currencyDB.disconnect();
                return currency;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Currency> getAllCurrency() {
        String getAllCurrencyCommand = "SELECT * FROM currencies";
        ArrayList<Currency> currencyList = new ArrayList<>();
        try {
            currencyDB.connect();
            ResultSet resultSet = currencyDB.getStatement().executeQuery(getAllCurrencyCommand);
            while (resultSet.next()) {
                Currency currency = new Currency();
                currency.setId(resultSet.getInt("id"));
                currency.setCode(resultSet.getString("code"));
                currency.setFullName(resultSet.getString("fullName"));
                currency.setSign(resultSet.getString("sign"));
                currencyList.add(currency);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        currencyDB.disconnect();
        return currencyList;
    }

    public ArrayList<ExchangeRate> getAllExchangeRates() {

        String getAllExchangeRatesCommand = "SELECT exchangeRates.id, base.Code AS Base, target.Code AS Target, exchangeRates.rate\n" +
                "FROM exchangeRates\n" +
                "INNER JOIN currencies base ON exchangeRates.BaseCurrencyId = base.ID\n" +
                "INNER JOIN currencies target ON exchangeRates.TargetCurrencyId = target.ID\n";

        ArrayList<ExchangeRate> exchangeRatesList = new ArrayList<>();
        try {
            currencyDB.connect();
            ResultSet resultSet = currencyDB.getStatement().executeQuery(getAllExchangeRatesCommand);
            while (resultSet.next()) {
                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setId(resultSet.getInt("id"));
                exchangeRate.setBaseCurrency(getCurrencyByCode(resultSet.getString("base")));
                exchangeRate.setTargetCurrency(getCurrencyByCode(resultSet.getString("target")));
                exchangeRate.setRate(resultSet.getDouble("rate"));
                exchangeRatesList.add(exchangeRate);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        currencyDB.disconnect();
        return exchangeRatesList;
    }

    public ExchangeRate getExchangeRateByCode(String baseCurrencyCode, String targetCurrencyCode) {

        String getExchangeRateByCode = "SELECT exchangeRates.id, base.Code AS Base, target.Code AS Target, exchangeRates.rate\n" +
                "FROM exchangeRates\n" +
                "INNER JOIN currencies base ON exchangeRates.BaseCurrencyId = base.ID\n" +
                "INNER JOIN currencies target ON exchangeRates.TargetCurrencyId = target.ID\n" +
                "WHERE Base='" + baseCurrencyCode + "' AND Target='" + targetCurrencyCode + "'";

        currencyDB.connect();
        try {
            ResultSet resultSet = currencyDB.getStatement().executeQuery(getExchangeRateByCode);
            if (!resultSet.next()) {
                resultSet.close();
                currencyDB.disconnect();
                return null;
            } else {
                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setId(resultSet.getInt("ID"));
                exchangeRate.setBaseCurrency(getCurrencyByCode(baseCurrencyCode));
                exchangeRate.setTargetCurrency(getCurrencyByCode(targetCurrencyCode));
                exchangeRate.setRate(resultSet.getDouble("rate"));
                resultSet.close();
                currencyDB.disconnect();
                return exchangeRate;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ExchangeRate> getExchangeThroughTransaction(String baseCurrency, String targetCurrency) {

        String getByExchangeRate = "SELECT exchangeRates.id, base.Code AS Base, target.Code AS Target, exchangeRates.rate\n" +
                "FROM exchangeRates\n" +
                "INNER JOIN currencies base ON exchangeRates.BaseCurrencyId = base.ID\n" +
                "INNER JOIN currencies target ON exchangeRates.TargetCurrencyId = target.ID\n" +
                "WHERE Base='USD' AND Target='" + baseCurrency + "' OR Base='USD' AND Target='" + targetCurrency + "'";

        try {
            currencyDB.connect();
            ResultSet resultSet = currencyDB.getStatement().executeQuery(getByExchangeRate);
            ArrayList<ExchangeRate> exchangeRatesForTransaction = new ArrayList<>();
            if (!resultSet.next()) {
                resultSet.close();
                currencyDB.disconnect();
                return null;
            } else {
                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setId(resultSet.getInt("id"));
                exchangeRate.setBaseCurrency(getCurrencyByCode(resultSet.getString("base")));
                exchangeRate.setTargetCurrency(getCurrencyByCode(resultSet.getString("target")));
                exchangeRate.setRate(resultSet.getDouble("rate"));

                exchangeRatesForTransaction.add(exchangeRate);

                resultSet.close();
                currencyDB.disconnect();
                return exchangeRatesForTransaction;
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertExchangeRate(Currency baseCurrency, Currency targetCurrency, String rateExchange) {

        String insertExchangeRateCommand = "INSERT INTO exchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) " +
                "VALUES ('" + baseCurrency.getId() + "', '" + targetCurrency.getId() + "', '" + rateExchange + "')";

        try {
            currencyDB.connect();
            currencyDB.getStatement().executeUpdate(insertExchangeRateCommand);
            currencyDB.disconnect();
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void patchExchangeRate(Currency baseCurrency, Currency targetCurrency, String rateExchange) {

        String getByCode = "UPDATE exchangeRates SET rate='" + Double.parseDouble(rateExchange) +
                "' WHERE BaseCurrencyId='" + baseCurrency.getId() +
                "' AND TargetCurrencyId='" + targetCurrency.getId() + "'";

        currencyDB.connect();
        try {
            currencyDB.getStatement().executeUpdate(getByCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        currencyDB.disconnect();
    }
}
