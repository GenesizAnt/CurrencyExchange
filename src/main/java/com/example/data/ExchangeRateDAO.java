package com.example.data;

import com.example.dto.ExchangeRateDTO;
import com.example.entity.Currency;
import com.example.entity.ExchangeRate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDAO extends EntityDAO {

    private ConnectionPool connectionPool;

    public Optional<List<ExchangeRate>> getAllExchangeRates() {
        String getAllExchangeRateCommand = "SELECT * FROM exchangeRates";

        Connection connection = getConnectionPool();
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
                getPool().releaseConnection(connection);
                return Optional.of(exchangeRates);
            } else {
                getPool().releaseConnection(connection);
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

        Connection connection = getConnectionPool();
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
                    getPool().releaseConnection(connection);
                    return Optional.of(exchangeRate);
                } else {
                    getPool().releaseConnection(connection);
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
