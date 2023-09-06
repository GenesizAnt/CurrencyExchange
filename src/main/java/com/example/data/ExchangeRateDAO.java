package com.example.data;

import com.example.dto.ExchangeRateDTO;
import com.example.entity.ExchangeRate;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDAO extends EntityDAO {

//    private ConnectionPool connectionPool;

    public Optional<List<ExchangeRate>> getAllExchangeRates() {
        String getAllExchangeRateCommand = "SELECT * FROM exchangeRates";

        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(getAllExchangeRateCommand);

            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.isBeforeFirst()) {
                List<ExchangeRate> exchangeRates = new ArrayList<>();
                while (resultSet.next()) {
                    exchangeRates.add(new ExchangeRate(
                            resultSet.getInt("id"),
                            resultSet.getInt("baseCurrencyId"),
                            resultSet.getInt("targetCurrencyId"),
                            resultSet.getBigDecimal("rate")));
                }
                dialOut(connection, statement);
                return Optional.of(exchangeRates);
            } else {
                dialOut(connection, statement);
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<ExchangeRate> getExchangeRateCode(String baseCurrencyCode, String targetCurrencyCode) {
        String getExchangeRateByCodeCommand = "SELECT exchangeRates.id, base.id AS Base, target.id AS Target, exchangeRates.rate\n" +
                "FROM exchangeRates\n" +
                "INNER JOIN currencies base ON exchangeRates.BaseCurrencyId = base.ID\n" +
                "INNER JOIN currencies target ON exchangeRates.TargetCurrencyId = target.ID\n" +
                "WHERE Base.code = ? AND Target.code = ?";

        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(getExchangeRateByCodeCommand);
            statement.setString(1, baseCurrencyCode);
            statement.setString(2, targetCurrencyCode);

            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.isBeforeFirst()) {
                if (resultSet.next()) {
                    ExchangeRate exchangeRate = new ExchangeRate(
                            resultSet.getInt("id"),
                            resultSet.getInt("base"),
                            resultSet.getInt("target"),
                            resultSet.getBigDecimal("rate"));
                    dialOut(connection, statement);
                    return Optional.of(exchangeRate);
                } else {
                    dialOut(connection, statement);
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        getPool().releaseConnection(connection);
        return Optional.empty();
    }

    public void insertExchangeRate(int baseCurrencyId, int targetCurrencyId, BigDecimal rate) {
        String insertExchangeCommand = "INSERT INTO exchangeRates (baseCurrencyId, targetCurrencyId, rate) VALUES (?, ?, ?)";

        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(insertExchangeCommand);

            statement.setInt(1, baseCurrencyId);
            statement.setInt(2, targetCurrencyId);
            statement.setBigDecimal(3, rate);
            statement.executeUpdate();

            dialOut(connection, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void patchExchangeRate(ExchangeRateDTO exchangeRateDTO, BigDecimal rate) {

        String getByCodeCommand = "UPDATE exchangeRates SET rate = ? WHERE BaseCurrencyId = ? AND TargetCurrencyId = ?";


        Connection connection = getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(getByCodeCommand);

            statement.setBigDecimal(1, rate);
            statement.setInt(2, exchangeRateDTO.getBaseCurrency().getId());
            statement.setInt(3, exchangeRateDTO.getTargetCurrency().getId());

            statement.executeUpdate();
            dialOut(connection, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void dialOut(Connection connection, PreparedStatement statement) throws SQLException {
        statement.close();
        getPool().releaseConnection(connection);
    }
}
