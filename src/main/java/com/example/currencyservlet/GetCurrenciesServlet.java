package com.example.currencyservlet;

import com.example.currencyexchange.Currency;
import com.example.currencyexchange.CurrencyDB;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class GetCurrenciesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        try {

            CurrencyDB currencyDB = new CurrencyDB();
            String getAllCommand = "SELECT * FROM currencies";
            PrintWriter pw = response.getWriter();

            try {
                ResultSet resultSet = currencyDB.getStatement().executeQuery(getAllCommand);
                List<Currency> list = new ArrayList<>();

                while (resultSet.next()) {
                    Currency currency = new Currency();
                    currency.setId(resultSet.getInt("id"));
                    currency.setCode(resultSet.getString("code"));
                    currency.setFullName(resultSet.getString("fullName"));
                    currency.setSign(resultSet.getString("sign"));
                    list.add(currency);
                }

                pw.println("[");
                for (int i = 0; i < list.size(); i++) {
                    if (i == list.size() - 1) {
                        pw.println(list.get(i).toString());
                    } else {
                        pw.println(list.get(i).toString() + ",");
                    }
                    System.out.println();
                }
                pw.println("]");


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


//        } catch (IOException e) {
//            response.setStatus(200);//ToDo сделать ENUM из кодов на успех/ошибку
//            response.getWriter().println("Ошибка (например, база данных недоступна)");
//        } catch (RuntimeException e) {
//            response.setStatus(500);
//            response.getWriter().println("Ошибка (например, база данных недоступна)");
//        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
