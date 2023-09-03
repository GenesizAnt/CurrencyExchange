package com.example.data;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityDAO {

    private ConnectionPool connectionPool;

    public Connection getConnectionPool() {
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
