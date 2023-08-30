//package com.example.zdelete;
//
//import com.example.error.ErrorQuery;
//import com.example.entity.Currency;
//import com.example.test.CurrencyDAO;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//import static com.example.Util.getJsonResponse;
//
//public class TestQv {
//
//    CurrencyDAO currencyDAO = new CurrencyDAO();
//
//    public ArrayList<Currency> getAllCurrency(HttpServletResponse response) throws IOException {
//
//
//        ArrayList<Currency> allCurrency;
//
//        try {
//            allCurrency = currencyDAO.getAllCurrency();
//        } catch (Exception e) {
//            response.setStatus(500);
//            ErrorQuery errorQuery = new ErrorQuery("Database is unavailable - 500");
//            getJsonResponse(errorQuery, response);
//            throw new RuntimeException(e);
//        }
//
//        for (Currency currency : allCurrency) {
//            getJsonResponse(currency, response); // здесь должен быть ДТО
//        }
//
//        response.setStatus(200);
//        return allCurrency;
//    }
//
//}
