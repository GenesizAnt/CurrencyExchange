package com.example.currencyservlet;

import com.example.currencyexchange.ControlQuery;
import com.example.currencyexchange.ErrorQuery;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static com.example.Util.*;

@WebServlet("/exchangeRate/*")
public class ExchangeRateByCodeServlet extends HttpServlet {

    private ControlQuery control = new ControlQuery();
    private ErrorQuery errorQuery;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (isCorrectCodeExchangeRates(request)) {
            String exchangeRateCode = getCodeFromURL(request);
            control.getExchangeRate(exchangeRateCode, response);
        } else {
            response.setStatus(400);
            errorQuery = new ErrorQuery("Incorrect request - 400");
            getJsonResponse(errorQuery, response);
        }
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

//        ObjectMapper objectMapper = new ObjectMapper();

//        String parameter = request.getReader().readLine();
//        if (parameter == null || !parameter.contains("rate")) {
//            response.setStatus(SC_BAD_REQUEST);
////            objectMapper.writeValue(response.getWriter(), "Missing required parameter rate");
//            return;
//        }
        String empty = "";
        String rate = request.getParameter("rate");

        if (isCorrectCodeExchangeRates(request) && !(rate.equals(empty)) && rate.matches("\\d*[.]?\\d{1,6}\\b")
                && !(rate.matches("[a-zA-Zа-яА-Я]+"))) {
            String exchangeRateCode = getCodeFromURL(request);
            control.patchExchangeRate(exchangeRateCode, rate, response);
        } else {
            response.setStatus(400);
            errorQuery = new ErrorQuery("Incorrect request - 400");
            getJsonResponse(errorQuery, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
