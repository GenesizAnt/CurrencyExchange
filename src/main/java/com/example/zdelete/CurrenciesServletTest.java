package com.example.zdelete;

import com.example.entity.Currency;
import com.example.test.RequestValidation;
import com.example.zdelete.TestQv;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import static com.example.Util.getJsonResponse;

@WebServlet("/currenciesTest")
public class CurrenciesServletTest extends RequestValidation {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        getAllCurrency(response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
