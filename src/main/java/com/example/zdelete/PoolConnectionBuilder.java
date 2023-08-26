package com.example.zdelete;

import java.sql.Connection;
import java.sql.SQLException;

import org.sqlite.javax.SQLiteConnectionPoolDataSource;

public class PoolConnectionBuilder implements ConnectionBuilder {

    private static final String DB_URL = "jdbc:sqlite:Currencies.db";
//    private static final String DB_URL = "jdbc:sqlite:C:\\Users\\genes\\IdeaProjects\\1NewVolna\\CurrencyExchange\\Currencies.db";
    private static final int MAX_POOL_SIZE = 5;

    private static SQLiteConnectionPoolDataSource dataSource;

    public PoolConnectionBuilder() {
    }

    public static void init() {
        if (dataSource == null) {
            synchronized (PoolConnectionBuilder.class) {
                if (dataSource == null) {
                    dataSource = new SQLiteConnectionPoolDataSource();
                    dataSource.setUrl(DB_URL);
                    dataSource.setMaxPageCount(MAX_POOL_SIZE);
                }
            }
        }
    }

    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new IllegalStateException("Connection pool is not initialized");
        }
        return dataSource.getConnection();
    }










//    private DataSource dataSource;
//
//    public PoolConnectionBuilder() {
//        try {
//            Context context = new InitialContext();
//            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/CurrencyExchange");
//        } catch (NamingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public Connection getConnection() throws SQLException {
//        return dataSource.getConnection();
//    }
}
