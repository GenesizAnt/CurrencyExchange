package com.example.data;

import com.example.dto.CurrencyDTO;
import com.example.dto.ExchangeRateDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    public ExchangeTransaction(ExchangeRateDTO baseExchangeRateDTO, ExchangeRateDTO targetExchangeRateDTO) {
        this.baseCurrency = baseExchangeRateDTO.getTargetCurrency();
        this.targetCurrency = targetExchangeRateDTO.getTargetCurrency();
        this.rate = baseExchangeRateDTO.getRate().divide(targetExchangeRateDTO.getRate(), 2, RoundingMode.HALF_EVEN);
    }

    public void calculateExchangeTransaction(BigDecimal amount) {
        this.amount = amount;
        convertedAmount = amount.multiply(rate).setScale(2, RoundingMode.HALF_EVEN);
    }

    public void calculateReverseExchangeTransaction(BigDecimal amount) {
        this.amount = amount;
        this.convertedAmount = amount.divide(rate, 2, RoundingMode.HALF_EVEN);
    }

    public void calculateExchangeTransactionThroughUSD(BigDecimal amount, BigDecimal rateThroughUSD) {
        this.amount = amount;
        this.convertedAmount = amount.divide(rateThroughUSD, 2, RoundingMode.HALF_EVEN);
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

}
