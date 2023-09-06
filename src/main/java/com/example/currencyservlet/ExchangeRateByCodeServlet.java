package com.example.currencyservlet;

import com.example.controller.RequestValidation;
import com.example.error.ErrorQuery;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import static com.example.Util.*;

@WebServlet("/exchangeRate/*")
public class ExchangeRateByCodeServlet extends RequestValidation {

//    private QueriesControl queriesControl = new QueriesControl();
//    private ErrorQuery errorQuery;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        getExchangeRate(request, response);

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if (!(method.equals("PATCH"))) {
            super.service(req, resp);
            return;
        }
        this.doPatch(req, resp);
    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {

        patchExchangeRate(request, response);

//        String empty = "";
//        String rate = request.getParameter("rate");
//
//        if (isCorrectCodeExchangeRate(request) && !(rate.equals(empty)) && rate.matches("\\d*[.]?\\d{1,6}\\b")
//                && !(rate.matches("[a-zA-Zа-яА-Я]+"))) {
//            String exchangeRateCode = getCodeFromURL(request);
//            queriesControl.patchExchangeRate(exchangeRateCode, rate, response);
//        } else {
//            response.setStatus(400);
//            errorQuery = new ErrorQuery("Incorrect request - 400");
//            getJsonResponse(errorQuery, response);
//        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
