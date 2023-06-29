package com.example.currencyexchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Currency {

    private int id;
    private String code;
    private String fullName;
    private String sign;

    public Currency(int id, String code, String fullName, String sign) {
        this.id = id;
        this.code = code;
        this.fullName = fullName;
        this.sign = sign;
    }

    public Currency() {

    }


//    public String getJSONCurrency() throws JsonProcessingException {
//        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
//    }

//    @Override
//    public String toString() {
//        return "   {\n" +
//                "          \"id\": " + id + ",\n" +
//                "          \"code\": \"" + code + '\"' + ",\n" +
//                "          \"fullName\": \"" + fullName + '\"' + ",\n" +
//                "          \"sign\": \"" + sign + '\"' + "\n" +
//                "   }";
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
