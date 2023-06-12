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
import java.util.Queue;

public class GetCurrenciesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            CurrencyDB currencyDB = new CurrencyDB();
            String getAllCommand = "SELECT * FROM currencies";
            ObjectMapper objectMapper = new ObjectMapper();

            PrintWriter pw = response.getWriter();
//        pw.println("OK");
            StringBuilder personJSON = new StringBuilder();
            int countColumn = 1;
            Queue<Currency> currencyList = new ArrayDeque<>();

            try {
                ResultSet resultSet = currencyDB.getStatement().executeQuery(getAllCommand);

                while (resultSet.next()) {
                    personJSON.append(resultSet.getObject(countColumn));
//                    currencyList.add(objectMapper.readValue(String.valueOf(personJSON), Currency.class));
//                    countColumn++;
//                    if (countColumn % 4 == 0) {
//                        personJSON.delete(0, personJSON.length());
//                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            pw.println(personJSON);

//            while (!currencyList.isEmpty()) {
//                pw.println(currencyList.poll());
//            }

            response.setStatus(200);
        } catch (IOException e) {
            response.setStatus(500);
            response.getWriter().println("Ошибка (например, база данных недоступна)");
        } catch (RuntimeException e) {
            response.setStatus(500);
            response.getWriter().println("Ошибка (например, база данных недоступна)");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
