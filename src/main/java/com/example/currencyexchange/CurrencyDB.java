package com.example.currencyexchange;

import java.sql.*;

public class CurrencyDB {

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    public CurrencyDB() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:Currencies.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void disconnect() {
        try {
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Statement getStatement() {
        return statement;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }
}

