package com.example.data;

import com.example.entity.Currency;
import com.example.test.ConnectionPool;
import com.example.zdelete.ConnectionBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExchangeRateDAO {

    private ConnectionPool connectionPool;

    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Connection getConnectionPool() throws SQLException {
        return connectionPool.getConnection();
    }

    public ArrayList<Currency> getExchangeRateByCode(String baseCurrencyCode, String targetCurrencyCode) throws SQLException {

        String getExchangeRateByCodeCommand = "SELECT * FROM currencies";
        ArrayList<Currency> currencyList = new ArrayList<>();


        try(Connection connection = getConnectionPool();
            PreparedStatement statement = connection.prepareStatement(getExchangeRateByCodeCommand)) {

            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Currency currency = new Currency(
                            resultSet.getInt("id"),
                            resultSet.getString("code"),
                            resultSet.getString("fullName"),
                            resultSet.getString("sign"));
                    currencyList.add(currency);
                }
            }
            connectionPool.releaseConnection(connection);
        }
        return currencyList;
    }
}
