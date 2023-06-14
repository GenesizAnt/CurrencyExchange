package com.example.currencyservlet;

import com.example.currencyexchange.Currency;
import com.example.currencyexchange.CurrencyDB;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetCurrenciesByCodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CurrencyDB currencyDB = new CurrencyDB();

        String currentCode = request.getParameter("code");

        String getByCode = "SELECT * FROM currencies WHERE code='" + currentCode + "'";
        PrintWriter pw = response.getWriter();

        try {
            ResultSet resultSet = currencyDB.getStatement().executeQuery(getByCode);

            Currency currency = new Currency();
            currency.setId(resultSet.getInt("id"));
            currency.setCode(resultSet.getString("code"));
            currency.setFullName(resultSet.getString("fullName"));
            currency.setSign(resultSet.getString("sign"));

            pw.println(currency);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//ToDo если нет в базе установить код ошибки (400 какой то вроде)
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
