package com.example;

import com.example.currencyexchange.ExchangeRates;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.*;

import static com.example.Util.checkAmount;

public class Test {
    public static void main(String[] args) throws IOException {

        System.out.println(checkAmount("10k"));


        User user = new User();
        user.setName("Tom");
        user.setAge(32);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String ss = objectMapper.writerWithView(Viewss.ExchangeRatee.class).writeValueAsString(user);
            String aa = objectMapper.writerWithView(Viewss.ExchangeRateTransactione.class).writeValueAsString(user);
            System.out.println(ss);
            System.out.println(aa);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }



        URL url = new URL("http://example.com/servlet-url");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PATCH");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        String requestBody = "{\"key\": \"value\"}";
//
//        try (OutputStream outputStream = connection.getOutputStream()) {
//            outputStream.write(requestBody.getBytes());
//            outputStream.flush();
//        }

        int responseCode = connection.getResponseCode();
        String s = connection.getContentEncoding();
        System.out.println("Response Code: " + responseCode);

// Чтение ответа от сервера
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            System.out.println("Response: " + response.toString());
        }

//        connection.disconnect();



    }
}

interface Viewss {
    public static class ExchangeRatee {
    }

    public static class ExchangeRateTransactione extends ExchangeRatee {
    }
}

class User {
    @JsonView(Viewss.ExchangeRatee.class)
    private String name;

    @JsonView(Viewss.ExchangeRateTransactione.class)
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
