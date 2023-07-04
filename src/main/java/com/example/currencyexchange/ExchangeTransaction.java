package com.example.currencyexchange;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExchangeTransaction {

    private ExchangeRates exchangeRates; // база передает только обменные курсы, один или два если надо

    @JsonIgnore
    private ExchangeRates dopolnitelmnoyRates;

    private double amount; // считается после возвращения данных обмена из базы в классе КонтролКвери. Можно сдеать через один метод - передаем кол-во
    //в нем же считаем convertedAmount
    private double convertedAmount;

    public ExchangeTransaction(ExchangeRates exchangeRates, ExchangeRates dopolnitelmnoyRates) {
        this.exchangeRates = exchangeRates;
        this.dopolnitelmnoyRates = dopolnitelmnoyRates;
    }

    public ExchangeTransaction(ExchangeRates exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public ExchangeTransaction() {

    }

    public void calculateExchange(String amount) {
        this.amount = Integer.parseInt(amount);
        this.convertedAmount = Integer.parseInt(amount) * exchangeRates.getRate();
    }

    public void calculateReverseExchange(String amount) {
        this.amount = Integer.parseInt(amount);
        this.convertedAmount = Integer.parseInt(amount) / exchangeRates.getRate();
    }

    public void calculateThroughExchange(String amount) {
        this.amount = Integer.parseInt(amount);
        double firstExchange = Integer.parseInt(amount) * exchangeRates.getRate();
        this.convertedAmount = firstExchange / dopolnitelmnoyRates.getRate();
    }

    public ExchangeRates getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(ExchangeRates exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

//    @JsonProperty("fieldToIgnore")
    public ExchangeRates getDopolnitelmnoyRates() {
        return dopolnitelmnoyRates;
    }

    public void setDopolnitelmnoyRates(ExchangeRates dopolnitelmnoyRates) {
        this.dopolnitelmnoyRates = dopolnitelmnoyRates;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

}
