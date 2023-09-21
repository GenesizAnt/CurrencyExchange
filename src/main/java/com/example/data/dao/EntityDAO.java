package com.example.data.dao;

import com.example.data.ConnectionPool;
import com.example.data.CurrencyBDConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityDAO {

    private final ConnectionPool connectionPool;

    public Connection getConnection() {
        try {
            return connectionPool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public EntityDAO() {
        try {
            this.connectionPool = CurrencyBDConnectionPool.create();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ConnectionPool getPool() {
        return connectionPool;
    }
}
