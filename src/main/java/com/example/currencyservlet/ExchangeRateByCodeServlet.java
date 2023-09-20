package com.example.currencyservlet;

import com.example.controller.RequestValidation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/exchangeRate/*")
public class ExchangeRateByCodeServlet extends RequestValidation {

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

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
