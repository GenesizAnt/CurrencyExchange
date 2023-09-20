package com.example.entity;

import java.math.BigDecimal;

public class ExchangeRate {

    private int id;
    private int baseCurrency;
    private int targetCurrency;
    private BigDecimal rate;

    public ExchangeRate(int id, int baseCurrency, int targetCurrency, BigDecimal rate) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

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

}
