package com.example.data;

import com.example.dto.CurrencyDTO;
import com.example.dto.ExchangeRateDTO;
import com.example.entity.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

public class ExchangeTransaction {

    private CurrencyDTO baseCurrency;
    private CurrencyDTO targetCurrency;
    private BigDecimal rate;
    private BigDecimal amount;
    private BigDecimal convertedAmount;

    public ExchangeTransaction(CurrencyDTO baseCurrency, CurrencyDTO targetCurrency, BigDecimal rate) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }

    public ExchangeTransaction(ExchangeRateDTO exchangeRates) {
        this.baseCurrency = exchangeRates.getBaseCurrency();
        this.targetCurrency = exchangeRates.getTargetCurrency();
        this.rate = exchangeRates.getRate();
    }

    public ExchangeTransaction() {

    }

    public void calculateExchangeTransaction(BigDecimal amount) {
        this.amount = amount;
        convertedAmount = amount.multiply(rate).setScale(2, RoundingMode.HALF_DOWN);
    }

    public void calculateReverseExchangeTransaction(BigDecimal amount) {
        this.amount = amount;
        this.convertedAmount = amount.divide(rate,2, RoundingMode.HALF_DOWN);
    }

    public void calculateExchangeTransactionThroughUSD(BigDecimal amount, Optional<List<ExchangeRateDTO>> exchangeRatesThroughUSD) {
        this.amount = amount;
        BigDecimal exchangeOnUSD = amount.multiply(exchangeRatesThroughUSD.get().get(0).getRate()).setScale(2, RoundingMode.HALF_DOWN);
        this.convertedAmount = exchangeOnUSD.divide(exchangeRatesThroughUSD.get().get(1).getRate(), 2, RoundingMode.HALF_DOWN);
    }


    public CurrencyDTO getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(CurrencyDTO baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public CurrencyDTO getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(CurrencyDTO targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    //    public Currency getTargetCurrency() {
//        return targetCurrency;
//    }
//
//    public void setTargetCurrency(Currency targetCurrency) {
//        this.targetCurrency = targetCurrency;
//    }
//
//    public double getRate() {
//        return rate;
//    }
//
//    public void setRate(double rate) {
//        this.rate = rate;
//    }
//
//    public double getAmount() {
//        return amount;
//    }
//
//    public void setAmount(double amount) {
//        this.amount = amount;
//    }
//
//    public double getConvertedAmount() {
//        return convertedAmount;
//    }
//
//    public void setConvertedAmount(double convertedAmount) {
//        this.convertedAmount = convertedAmount;
//    }

}
