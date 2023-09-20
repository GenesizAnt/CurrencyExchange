package com.example.entity;

import java.math.BigDecimal;
import java.util.Optional;

public class ExchangeRate {

    private int id;
    private int baseCurrency;
    private int targetCurrency;
    private BigDecimal rate;

//    public ExchangeRate(int id, Object baseCurrency, Object targetCurrency, BigDecimal rate) {
//        this.id = id;
//        this.baseCurrency = (Optional<Currency>) baseCurrency;
//        this.targetCurrency = (Optional<Currency>) targetCurrency;
//        this.rate = rate;
//    }

    public ExchangeRate(int id, int baseCurrency, int targetCurrency, BigDecimal rate) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }


//    public ExchangeRate(int id, Currency baseCurrency, Currency targetCurrency, double rate) {
//        this.id = id;
//        this.baseCurrency = baseCurrency;
//        this.targetCurrency = targetCurrency;
//        this.rate = rate;
//    }

//    public ExchangeRate() {
//
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public Currency getBaseCurrency() {
//        return baseCurrency;
//    }
//
//    public void setBaseCurrency(Currency baseCurrency) {
//        this.baseCurrency = baseCurrency;
//    }
//
//    public Currency getTargetCurrency() {
//        return targetCurrency;
//    }
//
//    public void setTargetCurrency(Currency targetCurrency) {
//        this.targetCurrency = targetCurrency;
//    }

//
//    public Optional<Currency> getBaseCurrency() {
//        return baseCurrency;
//    }
//
//    public Optional<Currency> getTargetCurrency() {
//        return targetCurrency;
//    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
//
//    public void setBaseCurrency(Optional<Currency> base) {
//        baseCurrency = base;
//    }
//
//    public void setTargetCurrency(Optional<Currency> target) {
//        targetCurrency = target;
//    }

    public int getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(int baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public int getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(int targetCurrency) {
        this.targetCurrency = targetCurrency;
    }


    //    public double getRate() {
//        return rate;
//    }
//
//    public void setRate(double rate) {
//        this.rate = rate;
//    }
}
