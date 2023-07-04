package com.example.currencyservlet;

import com.example.currencyexchange.ControlQuery;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/exchange")
public class ExchangeCurrenciesServlet extends HttpServlet {

    private ControlQuery control = new ControlQuery();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String amount = request.getParameter("amount");

        control.getExchangeTransaction(from, to, amount, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
