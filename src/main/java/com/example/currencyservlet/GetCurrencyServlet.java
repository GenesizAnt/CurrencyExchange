package com.example.currencyservlet;

import com.example.controller.QueriesControl;
import com.example.data.ErrorQuery;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import static com.example.Util.*;

@WebServlet("/currency/*")
public class GetCurrencyServlet extends HttpServlet {

    private QueriesControl queriesControl = new QueriesControl();
    private ErrorQuery errorQuery;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isCorrectCodeCurrency(request)) {
            String currencyCode = getCodeFromURL(request);
            queriesControl.getCurrency(currencyCode, response);
        } else {
            response.setStatus(400);
            errorQuery = new ErrorQuery("Incorrect request - 400");
            getJsonResponse(errorQuery, response);
        }


        //ToDo см. ниже
//        try {
//            // достаем код валюты, внутри этой функции вызываем всю нужную валидацию
//            String code = getCodeFromURL(request);
//
//            // получаем валюту, если её нет - метод выкинет CurrencyNotFoundException
//            Currency currency = currencyDao.getByCode(code);
//
//            // ... сериализуем в JSON и отправляем в ответе
//        } catch (CurrencyNotFoundException e) {
//            sendError(404, "Currency with this code does not exist")
//        } catch (ValidationException e) {
//            sendError(400, e.getMessage());
//        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
