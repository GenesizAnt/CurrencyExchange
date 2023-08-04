package com.example.currencyservlet;

import com.example.controller.QueriesControl;
import com.example.entity.ErrorQuery;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import static com.example.Util.CORRECT_COUNT_LETTER_CURRENCY_NAME;
import static com.example.Util.getJsonResponse;

@WebServlet("/exchange")
public class ExchangeCurrenciesServlet extends HttpServlet {

    private QueriesControl queriesControl = new QueriesControl();
    private ErrorQuery errorQuery;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String empty = "";

        String baseCurrency = request.getParameter("from");
        String targetCurrency = request.getParameter("to");
        String amount = request.getParameter("amount");

        if (baseCurrency.equals(empty) || baseCurrency.length() != CORRECT_COUNT_LETTER_CURRENCY_NAME ||
                baseCurrency.matches("\\d+") || !(baseCurrency.matches("[a-zA-Z]+"))) {
            response.setStatus(400);
            errorQuery = new ErrorQuery("Base currency code " + baseCurrency + " is empty or incorrect - 400");
            getJsonResponse(errorQuery, response);
        } else if (targetCurrency.equals(empty) || targetCurrency.length() != CORRECT_COUNT_LETTER_CURRENCY_NAME ||
                targetCurrency.matches("\\d+") || !(targetCurrency.matches("[a-zA-Z]+"))) {
            response.setStatus(400);
            errorQuery = new ErrorQuery("Target currency code " + targetCurrency + " is empty or incorrect - 400");
            getJsonResponse(errorQuery, response);
        } else if (amount.equals(empty) || !(amount.matches("\\d*[.]?\\d{1,2}\\b"))) {
            response.setStatus(400);
            errorQuery = new ErrorQuery("Amount " + amount + " is empty or incorrect - 400");
            getJsonResponse(errorQuery, response);
        } else {

            queriesControl.getExchangeTransaction(baseCurrency, targetCurrency, amount, response);

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
