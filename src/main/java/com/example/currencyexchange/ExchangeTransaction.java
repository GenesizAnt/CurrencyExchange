package com.example.currencyexchange;

import java.util.ArrayList;

public class ExchangeTransaction {

    private Currency baseCurrency;
    private Currency targetCurrency;

//    private ExchangeRates exchangeRates; // база передает только обменные курсы, один или два если надо
//
//    private ExchangeRates dopolnitelmnoyRates;

    private double rate;
    private double amount; // считается после возвращения данных обмена из базы в классе КонтролКвери. Можно сдеать через один метод - передаем кол-во
    //в нем же считаем convertedAmount
    private double convertedAmount;


    public ExchangeTransaction(Currency baseCurrency, Currency targetCurrency) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
    }

    public ExchangeTransaction(PairCurrency pairCurrency) {
        this.baseCurrency = pairCurrency.getBaseCurrency();
        this.targetCurrency = pairCurrency.getTargetCurrency();
        this.rate = pairCurrency.getRate();
    }

    public ExchangeTransaction(ExchangeRates exchangeRates) {
        this.baseCurrency = exchangeRates.getBaseCurrency();
        this.targetCurrency = exchangeRates.getTargetCurrency();
        this.rate = exchangeRates.getRate();
    }

    public ExchangeTransaction() {

    }

    public void calculateExchange(int amount) {
        this.amount = amount;
        convertedAmount = amount * rate;
    }

    public void calculateReverseExchange(int amount) {
        this.amount = amount;
        this.convertedAmount = amount / rate;
    }

    public void calculateThroughExchange(int amount, ArrayList<ExchangeRates> exchangeThroughRate) {
        this.amount = amount;
        double firstExchange = amount * exchangeThroughRate.get(0).getRate();
        this.convertedAmount = firstExchange / exchangeThroughRate.get(1).getRate();
    }


    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
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
