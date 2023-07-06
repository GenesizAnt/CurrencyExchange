package com.example.currencyservlet;

import com.example.currencyexchange.ControlQuery;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/exchangeRates")
public class ExchangeRateServlet extends HttpServlet {

    private ControlQuery control = new ControlQuery();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        control.getAllExchangeRates(response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String baseCurrencyCodeExc = request.getParameter("baseCurrencyCode");
        String targetCurrencyCodeExc = request.getParameter("targetCurrencyCode");
        String rateExc = request.getParameter("rate");

        control.postExchangeRate(baseCurrencyCodeExc, targetCurrencyCodeExc, rateExc, response);

    }
}
