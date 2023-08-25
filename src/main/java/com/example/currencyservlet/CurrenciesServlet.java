package com.example.currencyservlet;

import com.example.controller.QueriesControl;
import com.example.data.ErrorQuery;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.*;
import java.io.IOException;

import static com.example.Util.CORRECT_COUNT_LETTER_CURRENCY_NAME;
import static com.example.Util.getJsonResponse;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {

    private QueriesControl queriesControl = new QueriesControl();
    private ErrorQuery errorQuery;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        queriesControl.getAllCurrency(response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String empty = "";

        String codeCurrency = request.getParameter("code");
        String nameCurrency = request.getParameter("name");
        String signCurrency = request.getParameter("sign");

        //ToDo создать ДТО и оправлять его на отдельную валидацию
        //ToDo ModelMapper что это для ДТО? Если нужно будет превращать одну дто в другую похожую, почитай про ModelMapper
        //ToDo Optional что это и как заменить НУЛЛ?

        if (codeCurrency.equals(empty) || codeCurrency.length() != CORRECT_COUNT_LETTER_CURRENCY_NAME ||
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

            queriesControl.postCurrency(codeCurrency, nameCurrency, signCurrency, response);

        }
    }
}
