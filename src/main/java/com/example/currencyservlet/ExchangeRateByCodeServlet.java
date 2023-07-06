package com.example.currencyservlet;

import com.example.currencyexchange.ControlQuery;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static com.example.Util.getCodeFromURL;

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
//        String rate = req.getParameter("rate");
        this.doPatch(req, resp);
    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) {

        String exchangeRateCode = getCodeFromURL(request);

        String rate = request.getParameter("rate");

        control.patchExchangeRate(exchangeRateCode, rate, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
