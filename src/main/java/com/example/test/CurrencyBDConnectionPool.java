package com.example.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyBDConnectionPool implements ConnectionPool {

    private static String url = "jdbc:sqlite:Currencies.db";
    private List connectionPool;
    private List usedConnections = new ArrayList<>();
    private static int INITIAL_POOL_SIZE = 5;
    private static int MAX_POOL_SIZE = 10;

    public static CurrencyBDConnectionPool create() throws SQLException {
        List pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(url));
        }
        return new CurrencyBDConnectionPool(pool);
    }

    private CurrencyBDConnectionPool(List connectionPool) {
        this.connectionPool = connectionPool;
    }

    private static Connection createConnection(String url) throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return DriverManager.getConnection(url);
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connectionPool.add(createConnection(url));
            } else {
                throw new RuntimeException(
                        "Maximum pool size reached, no available connections!");
            }
        }
        Connection connection = (Connection) connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
//        return null;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }


    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
