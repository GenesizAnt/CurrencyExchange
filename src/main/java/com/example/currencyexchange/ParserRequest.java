package com.example.currencyexchange;

import com.example.currencyexchange.ControlQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ParserRequest {

    private String url;
    private String query;
    private ControlQuery control = new ControlQuery();


//    String getAll = "/currencies";
//    String postNewCurrent = "/currencies";
//
//    String getByCode = "/currency/EUR";
//
//    String getAllChange = "/exchangeRates";
//    String getChangeByCode = "/exchangeRate/USDRUB";
//    String postNewChange = "/exchangeRates";
//    String patchChange = "/exchangeRate/USDRUB";

    //        String getByCode = "/currency/EUR";
//        //http://localhost:8080/CurrencyExchange_war_exploded/router/currency/USD


    public void parsingGetRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

//        String url = request.getRequestURL().toString();


//        String getAll = "/currencies";

//        String getByCode = "/currency/EUR";

//        String getAllChange = "/exchangeRates";
//        String getChangeByCode = "/exchangeRate/USDRUB";

        String getExchange = "/exchange?from=BASE_CURRENCY_CODE&to=TARGET_CURRENCY_CODE&amount=$AMOUNT";

        String[] splitURL = request.getRequestURL().toString().split("/");

        //["0 http:", "", "1 localhost:8080", "2 CurrencyExchang...", "3 controller", "4 router", "5 currency", "6 USD"]
        //        http://localhost:8080/CurrencyExchange_war_exploded/router/currency/USD
        switch (splitURL[5]) {
            case "currency" -> {
                if (splitURL.length > 6) {
                    control.getCurrency(splitURL[6], response);
                } else {
                    response.setStatus(400);// устанавливаем статус ошибки
                    response.setContentType("text/html;charset=UTF-8"); // указываем тип контента
                    PrintWriter writer = response.getWriter();
                    writer.println("<h1>Ошибка 400</h1>");
                    writer.print("<p>Код валюты отсутствует в адресе " + request.getRequestURI() + ". Статус ошибки - 400.</p>");
                }
            }
            case "currencies" -> control.getAllCurrency(response);
            case "exchangeRates" -> control.getAllExchangeRates(response);
            case "exchangeRate" -> {
                if (splitURL.length > 6 && splitURL[6].length() == 6) {
//                    control.getExchangeRate(splitURL[6], response);
                } else {
                    response.setStatus(400);// устанавливаем статус ошибки
                    response.setContentType("text/html;charset=UTF-8"); // указываем тип контента
                    PrintWriter writer = response.getWriter();
                    writer.println("<h1>Ошибка 400</h1>");
                    writer.print("<p>Код валюты отсутствует в адресе или указан неверно " + request.getRequestURI() + ". Статус ошибки - 400.</p>");
                }
            }




//            default -> System.out.println("Введена некорректная команда!");
        }

    }

//        if (splitURL[5].equals("currency")) {
//            control.getCurrency(splitURL[6], response);
//        }

    }



//public class ErrorServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setStatus(500); // устанавливаем статус ошибки
//        resp.setContentType("text/plain"); // указываем тип контента
//        StringBuilder errorMessage = new StringBuilder();
//        errorMessage.append("An error occurred:");
//        errorMessage.append(req.getRequestURI()); // выводим URL запроса
//        errorMessage.append(", Status: ");
//        String status = req.getStatus(); // получаем статус ответа
//        switch (status) {
//            case 404:
//                errorMessage.append("404 - Not Found");
//                break;
//            default:
//                errorMessage.append((int) status);
//        }
//        errorMessage.append("");
//// выводим текст ошибки
//                errorMessage.toString().getBytes();
//    }
//}
//
