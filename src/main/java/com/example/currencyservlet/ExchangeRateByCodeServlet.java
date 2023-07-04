package com.example.currencyservlet;

import com.example.currencyexchange.ControlQuery;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static com.example.Util.getCodeFromURL;

@WebServlet("/exchangeRate/*")
public class ExchangeRateByCodeServlet extends HttpServlet {

    private ControlQuery control = new ControlQuery();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String currencyCode = getCodeFromURL(request);
        control.getExchangeRate(currencyCode, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
