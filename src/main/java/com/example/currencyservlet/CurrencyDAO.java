package com.example.currencyservlet;

import com.example.currencyexchange.Currency;
import com.example.currencyexchange.CurrencyDB;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO {

    private CurrencyDB currencyDB;

    public CurrencyDAO() {
        this.currencyDB = new CurrencyDB();
    }

    public void getCurrencyByCode(String code) {

        String getByCode = "SELECT * FROM currencies WHERE code='" + code + "'";

        try {
            ResultSet resultSet = currencyDB.getStatement().executeQuery(getByCode);
            Currency currency = new Currency();

            currency.setId(resultSet.getInt("id"));
            currency.setCode(resultSet.getString("code"));
            currency.setFullName(resultSet.getString("fullName"));
            currency.setSign(resultSet.getString("sign"));
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Currency> getAllCurrent() {

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
}
