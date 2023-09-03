package com.example.zdelete;

import com.example.test.ConnectionBuilder;

import java.sql.Connection;
import java.sql.SQLException;

public class DirectConnectionBuilder implements ConnectionBuilder {

    @Override
    public Connection getConnection() throws SQLException {
        return null;
//        return DriverManager.getConnection("jdbc:sqlite:C:\\Users\\genes\\IdeaProjects\\1NewVolna\\CurrencyExchange\\Currencies.db");
    }
}
