package com.example.data;

import com.example.entity.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDAO extends EntityDAO {


//    private ConnectionPool connectionPool;

//    public Connection getConnectionPool() {
//        try {
//            return connectionPool.getConnection();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public CurrencyDbDAO() {
//        try {
//            this.connectionPool = CurrencyBDConnectionPool.create();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public Optional<Currency> getCurrencyByCode(String codeCurrency) {
        String getByCodeCommand = "SELECT * FROM currencies WHERE code = ?";

        Connection connection = getConnectionPool();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(getByCodeCommand);
            statement.setString(1, codeCurrency);

            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.isBeforeFirst()) {
                if (resultSet.next()) {
                    Currency currency = new Currency(
                            resultSet.getInt("id"),
                            resultSet.getString("code"),
                            resultSet.getString("fullName"),
                            resultSet.getString("sign"));
                    getPool().releaseConnection(connection);
                    return Optional.of(currency);
                } else {
                    getPool().releaseConnection(connection);
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getPool().releaseConnection(connection);
        return Optional.empty();
    }


//    public Optional<List<ExchangeRate>> getAllExchangeRates() {
//        String getAllExchangeRateCommand = "SELECT exchangeRates.id, base.Code AS Base, target.Code AS Target, exchangeRates.rate\n" +
//                "FROM exchangeRates\n" +
//                "INNER JOIN currencies base ON exchangeRates.BaseCurrencyId = base.ID\n" +
//                "INNER JOIN currencies target ON exchangeRates.TargetCurrencyId = target.ID\n";
//
//        try (Connection connection = getConnectionPool();
//             PreparedStatement statement = connection.prepareStatement(getAllExchangeRateCommand)) {
//            statement.execute();
//            ResultSet resultSet = statement.getResultSet();
//            if (resultSet.isBeforeFirst()) {
//                ArrayList<ExchangeRate> exchangeRates = new ArrayList<>();
//                while (resultSet.next()) {
//                    ExchangeRate exchangeRate = new ExchangeRate();
//                    exchangeRate.setId(resultSet.getInt("id"));
//                    exchangeRate.setBaseCurrency(getCurrencyByCode(resultSet.getString("base")));
//                    exchangeRate.setTargetCurrency(getCurrencyByCode(resultSet.getString("target")));
//                    exchangeRate.setRate(BigDecimal.valueOf(resultSet.getDouble("rate")));
//                    exchangeRates.add(exchangeRate);
//                }
//                return Optional.of(exchangeRates);
//            } else {
//                return Optional.empty();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public Optional<List<Currency>> getAllCurrency() {
        String getAllCurrencyCommand = "SELECT * FROM currencies";

        Connection connection = getConnectionPool();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(getAllCurrencyCommand);

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
                getPool().releaseConnection(connection);
                return Optional.of(currencyList);
            } else {
                getPool().releaseConnection(connection);
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertCurrency(String code, String name, String sign) {
        String insertCurrencyCommand = "INSERT INTO currencies (code, fullName, sign) VALUES (?, ?, ?)";

        Connection connection = getConnectionPool();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(insertCurrencyCommand);

            statement.setString(1, code);
            statement.setString(2, name);
            statement.setString(3, sign);
            statement.executeUpdate();

            getPool().releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public Optional<List<ExchangeRate>> getAllExchangeRates() {
//        String getAllExchangeRateCommand = "SELECT * FROM exchangeRates";
//
//        Connection connection = getConnectionPool();
//        PreparedStatement statement;
//        try {
//            statement = connection.prepareStatement(getAllExchangeRateCommand);
//
//            statement.execute();
//            ResultSet resultSet = statement.getResultSet();
//            if (resultSet.isBeforeFirst()) {
//                List<ExchangeRate> exchangeRates = new ArrayList<>();
//                while (resultSet.next()) {
//                    exchangeRates.add(new ExchangeRate(
//                            resultSet.getInt("id"),
//                            resultSet.getInt("baseCurrencyId"),
//                            resultSet.getInt("targetCurrencyId"),
//                            resultSet.getBigDecimal("rate")));
//                }
//                connectionPool.releaseConnection(connection);
//                return Optional.of(exchangeRates);
//            } else {
//                connectionPool.releaseConnection(connection);
//                return Optional.empty();
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }


    public Optional<Currency> getCurrencyById(Integer id) {
        String getByIdCommand = "SELECT * FROM currencies WHERE ID = ?";

        Connection connection = getConnectionPool();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(getByIdCommand);

            statement.setInt(1, id);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            if (resultSet.isBeforeFirst()) {
                if (resultSet.next()) {
                    Currency currency = new Currency(
                            resultSet.getInt("id"),
                            resultSet.getString("code"),
                            resultSet.getString("fullName"),
                            resultSet.getString("sign"));
                    getPool().releaseConnection(connection);
                    return Optional.of(currency);
                } else {
                    getPool().releaseConnection(connection);
                    return Optional.empty();
                }
            }
//            getPool().releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        getPool().releaseConnection(connection);
        return Optional.empty();
    }

//    public Optional<List<Currency>> getAllById(int b, int t) {
//        String getByIdCommand = "SELECT * FROM currencies WHERE ID IN (?, ?)";
//
//        try (Connection connection = getConnectionPool();
//             PreparedStatement statement = connection.prepareStatement(getByIdCommand)) {
//
//            statement.setInt(1, b);
//            statement.setInt(2, t);
//            statement.execute();
//            ResultSet resultSet = statement.getResultSet();
//            if (resultSet.isBeforeFirst()) {
//                List<Currency> currencies = new ArrayList<>();
//                while (resultSet.next()) {
//                    currencies.add(new Currency(
//                            resultSet.getInt("id"),
//                            resultSet.getString("code"),
//                            resultSet.getString("fullName"),
//                            resultSet.getString("sign")));
//                }
////                connectionPool.releaseConnection(connection);
//                return Optional.of(currencies);
//            } else {
//                connectionPool.releaseConnection(connection);
//                return Optional.empty();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}

