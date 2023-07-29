package com.example.currencyservlet;

import com.example.currencyexchange.ControlQuery;
import com.example.currencyexchange.ErrorQuery;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static com.example.Util.CORRECT_LETTER_COUNT_CURRENT_NAME;
import static com.example.Util.getJsonResponse;

@WebServlet("/exchange")
public class ExchangeCurrenciesServlet extends HttpServlet {

    private ControlQuery control = new ControlQuery();
    private ErrorQuery errorQuery;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String empty = "";

        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String amount = request.getParameter("amount");

//        control.getExchangeTransaction(from, to, amount, response);

        if (from.equals(empty) || from.length() != CORRECT_LETTER_COUNT_CURRENT_NAME ||
                from.matches("\\d+") || !(from.matches("[a-zA-Z]+"))) {
            response.setStatus(400);
            errorQuery = new ErrorQuery("Base currency code " + from + " is empty or incorrect - 400");
            getJsonResponse(errorQuery, response);
        } else if (to.equals(empty) || to.length() != CORRECT_LETTER_COUNT_CURRENT_NAME ||
                to.matches("\\d+") || !(to.matches("[a-zA-Z]+"))) {
            response.setStatus(400);
            errorQuery = new ErrorQuery("Target currency code " + to + " is empty or incorrect - 400");
            getJsonResponse(errorQuery, response);
        } else if (amount.equals(empty) || !(amount.matches("\\d*[.]?\\d{1,2}\\b"))) {
            response.setStatus(400);
            errorQuery = new ErrorQuery("Amount " + amount + " is empty or incorrect - 400");
            getJsonResponse(errorQuery, response);
        } else {

            control.getExchangeTransaction(from, to, amount, response);

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
