//package com.example.zdelete;
//
//import com.example.dto.CurrencyDTO;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Optional;
//
//public class JsonCreator {
//
//    static ObjectMapper objectMapper = new ObjectMapper();
//
//    public static <T> void getValue(T t, HttpServletResponse response) throws IOException {
//
//            Optional<CurrencyDTO> currencyDTO = (Optional<CurrencyDTO>) t;
//            String jsonResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(currencyDTO.get());
//            PrintWriter writer = response.getWriter();
//            writer.println(jsonResponse);
//
//    }
//
//
////    public static void getJsonResponse(Object obj, HttpServletResponse response) throws IOException {
//////        Optional<Currency> objectOptional = obj;
////        String jsonResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
////        PrintWriter writer = response.getWriter();
////        writer.println(jsonResponse);
////    }
//}
