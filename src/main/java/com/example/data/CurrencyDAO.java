package com.example.data;

import com.example.entity.Currency;
import com.example.error.DatabaseException;
import org.sqlite.SQLiteErrorCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDAO extends EntityDAO {
    public Optional<Currency> getCurrencyByCode(String codeCurrency) {
        String getByCodeCommand = "SELECT * FROM currencies WHERE code = ?";

        Connection connection = getConnection();
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
                    statement.close();
                    return Optional.of(currency);
                } else {
                    getPool().releaseConnection(connection);
                    statement.close();
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getPool().releaseConnection(connection);
        return Optional.empty();
    }

    public Optional<List<Currency>> getAllCurrency() {
        String getAllCurrencyCommand = "SELECT * FROM currencies";

        Connection connection = getConnection();
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
                dialOut(connection, statement);
                return Optional.of(currencyList);
            } else {
                dialOut(connection, statement);
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertCurrency(String code, String name, String sign) {
        String insertCurrencyCommand = "INSERT INTO currencies (code, fullName, sign) VALUES (?, ?, ?)";

        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(insertCurrencyCommand);

            statement.setString(1, code);
            statement.setString(2, name);
            statement.setString(3, sign);
            statement.executeUpdate();

            dialOut(connection, statement);
        } catch (SQLException e) {

            if (e.getErrorCode() == SQLiteErrorCode.SQLITE_CONSTRAINT.code && e.getMessage().contains("UNIQUE constraint failed")) {
                throw new DatabaseException("Currency with this code already exists - 409");
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    public Optional<Currency> getCurrencyById(Integer id) {
        String getByIdCommand = "SELECT * FROM currencies WHERE ID = ?";

        Connection connection = getConnection();
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

                    dialOut(connection, statement);
                    return Optional.of(currency);
                } else {
                    dialOut(connection, statement);
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    private void dialOut(Connection connection, PreparedStatement statement) throws SQLException {
        statement.close();
        getPool().releaseConnection(connection);
    }
}

