package com.example.test;

import com.example.data.ErrorQuery;
import com.example.entity.Currency;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.Util.getJsonResponse;

public class RequestValidation extends HttpServlet {

    CurrencyDAOtest currencyDAO = new CurrencyDAOtest();

    public void getAllCurrency(HttpServletResponse response) throws IOException {


        ArrayList<Currency> allCurrency;

        try {
            allCurrency = currencyDAO.getAllCurrency();
        } catch (Exception e) {
            response.setStatus(500);
            ErrorQuery errorQuery = new ErrorQuery("Database is unavailable - 500");
            getJsonResponse(errorQuery, response);
            throw new RuntimeException(e);
        }

        for (Currency currency : allCurrency) {
            getJsonResponse(currency, response); // здесь должен быть ДТО
        }

        response.setStatus(200);
    }

}
