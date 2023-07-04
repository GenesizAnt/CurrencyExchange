package com.example.currencyservlet;

import com.example.currencyexchange.ParserRequest;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * получается у меня будет сервлет-роутер, который будет слушать запросы - получать урл
 * - передавать его в класс ПарсерРоут -
 * Который будет передавать это в сервлетКонтроллер -
 * который будет ходить в базу и дать результат или ошибку
 */
@WebServlet("/router/*")
public class RouterServlet extends HttpServlet {

    private ParserRequest parser = new ParserRequest();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String getAll = "/currencies";
        String postNewCurrent = "/currencies";

        String getByCode = "/currency/USD";

        String getAllChange = "/exchangeRates";
        String getChangeByCode = "/exchangeRate/USDRUB";
        String postNewChange = "/exchangeRates";
        String patchChange = "/exchangeRate/USDRUB";

        String getExchange = "/exchange?from=BASE_CURRENCY_CODE&to=TARGET_CURRENCY_CODE&amount=$AMOUNT";


        parser.parsingGetRequest(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}


//@WebServlet("/controller/*")