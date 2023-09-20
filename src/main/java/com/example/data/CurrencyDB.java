//package com.example.data;
//
//import java.sql.*;
//
//public class CurrencyDB {
//
//    private Connection connection;
//    private Statement statement;
//
//    public CurrencyDB() {
//        try {
//            Class.forName("org.sqlite.JDBC");
//            connect();
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    public void disconnect() {
//        try {
//            statement.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            connection.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    public void connect() {
//        try {
////            connection = DriverManager.getConnection("jdbc:sqlite:/opt/tomcat/webapps/Currencies.db");
//            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\genes\\IdeaProjects\\1NewVolna\\CurrencyExchange\\Currencies.db");
////            connection = DriverManager.getConnection("jdbc:sqlite:Currencies1.db");
//            this.statement = connection.createStatement();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    public Statement getStatement() {
//        return statement;
//    }
//
//}
//
