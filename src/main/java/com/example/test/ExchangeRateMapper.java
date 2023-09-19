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
                    CurrencyDTO baseCurrency = currencyService.getCurrencyById(exchangeRate.getBaseCurrency()).get();
                    CurrencyDTO targetCurrency = currencyService.getCurrencyById(exchangeRate.getTargetCurrency()).get();

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

                return currencyService.getCurrencyById(source).orElse(null);

            }
        });
        return mapper.map(exchangeRate, ExchangeRateDTO.class);
    }

    public ExchangeRateDTO toModel(Map<String, String> requestParameter) {
        ModelMapper modelMapper = new ModelMapper();
        ExchangeRateDTO exchangeRateDTO = modelMapper.map(requestParameter, ExchangeRateDTO.class);
        exchangeRateDTO.setBaseCurrency(currencyService.getCurrencyByCode(requestParameter.get("baseCurrencyCode")).get());
        exchangeRateDTO.setTargetCurrency(currencyService.getCurrencyByCode(requestParameter.get("targetCurrencyCode")).get());
        return exchangeRateDTO;
    }
}