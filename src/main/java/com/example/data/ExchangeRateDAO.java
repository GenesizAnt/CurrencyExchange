package com.example.data;

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
}
