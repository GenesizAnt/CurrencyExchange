package com.example.currencyservlet;

import com.example.controller.RequestValidation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.*;
import java.io.IOException;

import static com.example.Util.CORRECT_COUNT_LETTER_CURRENCY_NAME;
import static com.example.Util.getJsonResponse;

@WebServlet("/currencies")
public class CurrenciesServlet extends RequestValidation {

//    private QueriesControl queriesControl = new QueriesControl();
//    private ErrorQuery errorQuery;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        getAllCurrency(response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        postCurrency(request, response);

    }
}
