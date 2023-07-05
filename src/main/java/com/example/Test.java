package com.example;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {
    public static void main(String[] args) {

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
