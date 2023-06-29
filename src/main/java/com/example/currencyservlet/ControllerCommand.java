package com.example.currencyservlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.net.URLDecoder;


@WebServlet("/controller/*")
public class ControllerCommand extends HttpServlet {

    private final CurrencyDAO currencyDAO;

    private ParserRequest parser = new ParserRequest();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    public ControllerCommand() {
        this.currencyDAO = new CurrencyDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String getAll = "/currencies";
        String postNewCurrent = "/currencies";

        String getByCode = "/currency/EUR";

        String getAllChange = "/exchangeRates";
        String getChangeByCode = "/exchangeRate/USDRUB";
        String postNewChange = "/exchangeRates";
        String patchChange = "/exchangeRate/USDRUB";

        String getExchange = "/exchange?from=BASE_CURRENCY_CODE&to=TARGET_CURRENCY_CODE&amount=$AMOUNT";


        parser.parsingGetRequest(request, response);


        String command = request.getParameter("command");
        String currentCode = request.getParameter("code");
//        PrintWriter pw = response.getWriter();
//        pw.println("fvfvdfv");

        //http://localhost:8080/CurrencyExchange_war_exploded/controller?command=getAll
        if (command.equalsIgnoreCase("getAll")) {
            getServletContext().getRequestDispatcher("/showAllCurrent.jsp").forward(request, response);
        } else if (command.equalsIgnoreCase("get")) {

            //http://localhost:8080/CurrencyExchange_war_exploded/controller?command=get&code=RUB
            if (currencyDAO.isCurrentCode(currentCode)) {
                request.setAttribute("currentCode", currentCode);
                getServletContext().getRequestDispatcher("/showCurrentByCode.jsp").forward(request, response);
            } else {
                response.setStatus(505);
            }

        } else if (command.equalsIgnoreCase("post")) {

            getServletContext().getRequestDispatcher("/postCurrent.jsp").forward(request, response);

//            http://localhost:8080/CurrencyExchange_war_exploded/controller?val1=RRR&val2=RRR&amount=R

//            String codeCurrency = request.getParameter("val1");
//            String nameCurrency = request.getParameter("val2");
//            String signCurrency = request.getParameter("amount");

//            String url = request.getRequestURL().toString();
//
//            HashMap<String, String> queryParams = getQueryParams(url);

//            ToDo if (true) {
//                //проверить есть ли все поля формы?
//                // проверить есть ли уже такая валюта???
//                // Ошибка (например, база данных недоступна)
//            }

//            currencyDAO.postCurrency(codeCurrency, nameCurrency, signCurrency);

        } else if (command.equalsIgnoreCase(null)) {
//            PrintWriter pw = response.getWriter();
//            pw.println("fv");
        }
    }

    //ToDo этот метод работает на получение данных их формы
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter pw = resp.getWriter();
        pw.println("post");

//        String codeCurrency = request.getParameter("val1");
//        String nameCurrency = request.getParameter("val2");
//        String signCurrency = request.getParameter("amount");
//
//        currencyDAO.postCurrency(codeCurrency, nameCurrency, signCurrency);
    }

    protected void createCurrency(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

        String codeCurrency = request.getParameter("val1");
        String nameCurrency = request.getParameter("val2");
        String signCurrency = request.getParameter("amount");

        currencyDAO.postCurrency(codeCurrency, nameCurrency, signCurrency);
    }



    private HashMap<String, String> getQueryParams(String url) {
        try {
            URL urlRequest = new URL(url);
            String query = urlRequest.getQuery();

            HashMap<String, String> params = new HashMap<>();
            String[] keyValuePairs = query.split("&");
            for (String pair : keyValuePairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    String key = URLDecoder.decode(keyValue[0], "UTF-8");
                    String value = URLDecoder.decode(keyValue[1], "UTF-8");
                    params.put(key, value);
                }
            }
            return params;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
