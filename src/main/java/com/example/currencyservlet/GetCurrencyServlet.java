package com.example.currencyservlet;

import com.example.currencyexchange.ControlQuery;
import com.example.currencyexchange.ErrorQuery;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static com.example.Util.*;

@WebServlet("/currency/*")
public class GetCurrencyServlet extends HttpServlet {

    private ControlQuery control = new ControlQuery();
    private ErrorQuery errorQuery;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


//        if (isCorrectCodeExchangeRates(request)) {
//            String exchangeRateCode = getCodeFromURL(request);
//            control.getExchangeRate(exchangeRateCode, response);
//        } else {
//            response.setStatus(400);
//            errorQuery = new ErrorQuery("Incorrect request - 400");
//            getJsonResponse(errorQuery, response);
//        }

        if (isCorrectCodeCurrency(request)) {
            String currencyCode = getCodeFromURL(request);
            control.getCurrency(currencyCode, response);
        } else {
            response.setStatus(400);
            errorQuery = new ErrorQuery("Incorrect request - 400");
            getJsonResponse(errorQuery, response);
        }

//        String currencyCode = getCodeFromURL(request);
//        control.getCurrency(currencyCode, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
