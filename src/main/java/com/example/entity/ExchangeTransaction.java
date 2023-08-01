package com.example.entity;

import java.util.ArrayList;

public class ExchangeTransaction {

    private Currency baseCurrency;
    private Currency targetCurrency;
    private double rate;
    private double amount;
    private double convertedAmount;

    public ExchangeTransaction(Currency baseCurrency, Currency targetCurrency) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
    }

    public ExchangeTransaction(ExchangeRate exchangeRates) {
        this.baseCurrency = exchangeRates.getBaseCurrency();
        this.targetCurrency = exchangeRates.getTargetCurrency();
        this.rate = exchangeRates.getRate();
    }

    public ExchangeTransaction() {

    }

    public void calculateExchangeTransaction(double amount) {
        this.amount = amount;
        convertedAmount = amount * rate;
    }

    public void calculateReverseExchangeTransaction(double amount) {
        this.amount = amount;
        this.convertedAmount = amount / rate;
    }

    public void calculateExchangeTransactionThroughUSD(double amount, ArrayList<ExchangeRate> exchangeRatesThroughUSD) {
        this.amount = amount;
        double ExchangeOnUSD = amount * exchangeRatesThroughUSD.get(0).getRate();
        this.convertedAmount = ExchangeOnUSD / exchangeRatesThroughUSD.get(1).getRate();
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
