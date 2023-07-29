package com.example.currencyservlet;

import com.example.currencyexchange.ControlQuery;
import com.example.currencyexchange.CurrencyDAO;
import com.example.currencyexchange.ErrorQuery;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

import static com.example.Util.CORRECT_LETTER_COUNT_CURRENT_NAME;
import static com.example.Util.getJsonResponse;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {

    private ControlQuery control = new ControlQuery();
    private ErrorQuery errorQuery;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        control.getAllCurrency(response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String empty = "";

        String codeCurrency = request.getParameter("code");
        String nameCurrency = request.getParameter("name");
        String signCurrency = request.getParameter("sign");

        if (codeCurrency.equals(empty) || codeCurrency.length() != CORRECT_LETTER_COUNT_CURRENT_NAME ||
                codeCurrency.matches("\\d+") || !(codeCurrency.matches("[a-zA-Z]+"))) {
            response.setStatus(400);
            errorQuery = new ErrorQuery("Code currency " + codeCurrency + " is empty or incorrect - 400");
            getJsonResponse(errorQuery, response);
        } else if (nameCurrency.equals(empty)) {
            response.setStatus(400);
            errorQuery = new ErrorQuery("Name currency " + nameCurrency + " is empty or incorrect - 400");
            getJsonResponse(errorQuery, response);
        } else if (signCurrency.equals(empty) || !(signCurrency.matches("\\p{ASCII}")) || signCurrency.matches("\\d+")) {
            response.setStatus(400);
            errorQuery = new ErrorQuery("Sign currency " + signCurrency + " is empty or incorrect - 400");
            getJsonResponse(errorQuery, response);
        } else {

            control.postCurrency(codeCurrency, nameCurrency, signCurrency, response);

        }
    }

}
