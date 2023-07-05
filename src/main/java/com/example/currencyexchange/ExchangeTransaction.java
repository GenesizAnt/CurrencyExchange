package com.example.currencyexchange;

import java.util.ArrayList;

public class ExchangeTransaction extends PairCurrency {

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

    public ExchangeTransaction() {

    }

    public void calculateExchange(String amount) {
        this.amount = Integer.parseInt(amount);
        convertedAmount = Integer.parseInt(amount) * rate;
    }

    public void calculateReverseExchange(String amount) {
        this.amount = Integer.parseInt(amount);
        this.convertedAmount = Integer.parseInt(amount) / rate;
    }

    public void calculateThroughExchange(String amount, ArrayList<PairCurrency> exchangeThroughRate) {
        this.amount = Integer.parseInt(amount);
        double firstExchange = Integer.parseInt(amount) * exchangeThroughRate.get(0).getRate();
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
