package com.example.currencyservlet;

import com.example.currencyexchange.ControlQuery;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static com.example.Util.getCodeFromURL;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

@WebServlet("/exchangeRate/*")
public class ExchangeRateByCodeServlet extends HttpServlet {

    private ControlQuery control = new ControlQuery();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String exchangeRateCode = getCodeFromURL(request);
        control.getExchangeRate(exchangeRateCode, response);

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

        String exchangeRateCode = getCodeFromURL(request);

        String rate = request.getParameter("rate");

        control.patchExchangeRate(exchangeRateCode, rate, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
