package com.example.currencyservlet;

import com.example.currencyexchange.ControlQuery;
import com.example.currencyexchange.ErrorQuery;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static com.example.Util.CORRECT_LETTER_COUNT_CURRENT_NAME;
import static com.example.Util.getJsonResponse;

@WebServlet("/exchangeRates")
public class ExchangeRateServlet extends HttpServlet {

    private ControlQuery control = new ControlQuery();
    private ErrorQuery errorQuery;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        control.getAllExchangeRates(response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String empty = "";

        String baseCurrencyCodeExc = request.getParameter("baseCurrencyCode");
        String targetCurrencyCodeExc = request.getParameter("targetCurrencyCode");
        String rateExc = request.getParameter("rate");

        if (baseCurrencyCodeExc.equals(empty) || baseCurrencyCodeExc.length() != CORRECT_LETTER_COUNT_CURRENT_NAME ||
                baseCurrencyCodeExc.matches("\\d+") || !(baseCurrencyCodeExc.matches("[a-zA-Z]+"))) {
            response.setStatus(400);
            errorQuery = new ErrorQuery("Base currency code" + baseCurrencyCodeExc + " is empty or incorrect - 400");
            getJsonResponse(errorQuery, response);
        } else if (targetCurrencyCodeExc.equals(empty) || targetCurrencyCodeExc.length() != CORRECT_LETTER_COUNT_CURRENT_NAME ||
                targetCurrencyCodeExc.matches("\\d+") || !(targetCurrencyCodeExc.matches("[a-zA-Z]+"))) {
            response.setStatus(400);
            errorQuery = new ErrorQuery("Target currency code" + targetCurrencyCodeExc + " is empty or incorrect - 400");
            getJsonResponse(errorQuery, response);
        } else if (rateExc.equals(empty) || !(rateExc.matches("^\\d+\\.\\d{1,6}$")) || rateExc.matches("[a-zA-Zа-яА-Я]+")) {
            response.setStatus(400);
            errorQuery = new ErrorQuery("Rate exchange" + rateExc + " is empty or incorrect - 400");
            getJsonResponse(errorQuery, response);
        } else {

            control.postExchangeRate(baseCurrencyCodeExc, targetCurrencyCodeExc, rateExc, response);

        }
    }
}
