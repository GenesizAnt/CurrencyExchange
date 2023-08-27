package com.example.test;

import com.example.entity.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDAO {


    private ConnectionPool connectionPool;

//    public void setConnectionPool(ConnectionPool connectionPool) {
//        this.connectionPool = connectionPool;
//    }

    public Connection getConnectionPool() {
        try {
            return connectionPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CurrencyDAO() {
        try {
            this.connectionPool = CurrencyBDConnectionPool.create();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Currency getCurrencyByCode(String codeCurrency) {
        String getByCodeCommand = "SELECT * FROM currencies WHERE code = ?";

        try (Connection connection = getConnectionPool();
             PreparedStatement statement = connection.prepareStatement(getByCodeCommand)) {

            statement.setString(1, codeCurrency);

            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.isBeforeFirst()) {
                if (resultSet.next()) {
                    return new Currency(
                            resultSet.getInt("id"),
                            resultSet.getString("code"),
                            resultSet.getString("fullName"),
                            resultSet.getString("sign"));
                } else {
                    return null; //должен возвращать это Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; //должен возвращать это Optional.empty();
    }

    public Optional<List<Currency>> getAllCurrency() {      //метод готов по новым правилам
        String getAllCurrencyCommand = "SELECT * FROM currencies";

        try (Connection connection = getConnectionPool();
             PreparedStatement statement = connection.prepareStatement(getAllCurrencyCommand)) {

            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.isBeforeFirst()) {
                ArrayList<Currency> currencyList = new ArrayList<>();
                while (resultSet.next()) {
                    currencyList.add(new Currency(
                            resultSet.getInt("id"),
                            resultSet.getString("code"),
                            resultSet.getString("fullName"),
                            resultSet.getString("sign")));
                }
                return Optional.of(currencyList);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

