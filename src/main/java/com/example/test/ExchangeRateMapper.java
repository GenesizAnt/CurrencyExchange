package com.example.test;

import com.example.dto.CurrencyDTO;
import com.example.dto.ExchangeRateDTO;
import com.example.entity.Currency;
import com.example.entity.ExchangeRate;
import com.example.error.CurrencyNotFoundException;
import com.example.servise.CurrencyService;
import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

public class ExchangeRateMapper {

    private final CurrencyService currencyService;

    public ExchangeRateMapper() {
        this.currencyService = new CurrencyService();
    }

    private ModelMapper getMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        return mapper;
    }

    public List<ExchangeRateDTO> toDtoList(List<ExchangeRate> exchangeRates) {

        ModelMapper mapper = getMapper();
        List<ExchangeRateDTO> list = exchangeRates.stream()
                .map(exchangeRate -> {
                    ExchangeRateDTO exchangeRateDTO = mapper.map(exchangeRate, ExchangeRateDTO.class);
//                    List<CurrencyDTO> byId = currencyService.getById(exchangeRate.getBaseCurrency(), exchangeRate.getTargetCurrency());
//                    exchangeRateDTO.setBaseCurrency(byId.get(0));
//                    exchangeRateDTO.setTargetCurrency(byId.get(1));
                    CurrencyDTO baseCurrency;
                    CurrencyDTO targetCurrency;
                    try {
                        baseCurrency = currencyService.getCurrencyById(exchangeRate.getBaseCurrency()).get();
                        targetCurrency = currencyService.getCurrencyById(exchangeRate.getBaseCurrency()).get();
                    } catch (CurrencyNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    exchangeRateDTO.setBaseCurrency(baseCurrency);
                    exchangeRateDTO.setTargetCurrency(targetCurrency);
                    return exchangeRateDTO;
                })
                .collect(Collectors.toList());
        return list;

//        List<ExchangeRateDTO> list = new ArrayList<>();
//
//        for (ExchangeRate exchangeRate : exchangeRates) {
//            List<CurrencyDTO> byId = currencyService.getById(exchangeRate.getBaseCurrency(), exchangeRate.getTargetCurrency());
//            list.add(
//                    new ExchangeRateDTO(
//                            exchangeRate.getId(),
//                            byId.get(0),
//                            byId.get(1),
//                            exchangeRate.getRate()
//                    )
//            );
//        }
//        return list;
    }
}
