package com.example.test;

import com.example.dto.CurrencyDTO;
import com.example.dto.ExchangeRateDTO;
import com.example.entity.ExchangeRate;
import com.example.error.CurrencyNotFoundException;
import com.example.servise.CurrencyService;
import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.entry;
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

    public ExchangeRateDTO toDto(ExchangeRate exchangeRate) {
        ModelMapper mapper = getMapper();
        mapper.addConverter(new AbstractConverter<Integer, CurrencyDTO>() {
            @Override
            protected CurrencyDTO convert(Integer source) {
                try {
                    return currencyService.getCurrencyById(source).orElse(null);
                } catch (CurrencyNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return mapper.map(exchangeRate, ExchangeRateDTO.class);
    }

    public ExchangeRateDTO toModel(Map<String, String> requestParameter) {
        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
        try {
            exchangeRateDTO.setBaseCurrency(currencyService.getCurrencyByCode(requestParameter.get("baseCurrencyCode")).get());
            exchangeRateDTO.setTargetCurrency(currencyService.getCurrencyByCode(requestParameter.get("targetCurrencyCode")).get());
            exchangeRateDTO.setRate(BigDecimal.valueOf(Double.parseDouble(requestParameter.get("rate"))));
        } catch (CurrencyNotFoundException e) {
            throw new RuntimeException(e);
        }

//        entry("baseCurrencyCode", baseCurrencyCode),
//                entry("targetCurrencyCode", targetCurrencyCode),
//                entry("rate", rate));

//        ModelMapper mapper = getMapper();
//        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
//        mapper.addConverter(new AbstractConverter<String, CurrencyDTO>() {
//            @Override
//            protected CurrencyDTO convert(String source) {
//                try {
//                    return currencyService.getCurrencyByCode(source).orElse(null);
//                } catch (CurrencyNotFoundException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//        return mapper.map(exchangeRateDTO, ExchangeRateDTO.class);

        return exchangeRateDTO;
    }
}
