package com.example.dto;

import com.example.entity.Currency;
import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.Optional;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

public class CurrencyMapper {
    private ModelMapper getMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        return mapper;
    }

    public CurrencyDTO toDto(Optional<Currency> currency) {
        ModelMapper mapper = getMapper();
        TypeMap<Currency, CurrencyDTO> propertyMapper = mapper.createTypeMap(Currency.class, CurrencyDTO.class);
        propertyMapper.addMapping(Currency::getFullName, CurrencyDTO::setName);
        return mapper.map(currency, CurrencyDTO.class);
    }

    public List<CurrencyDTO> toDtoList(List<Currency> currencies) {
        ModelMapper mapper = getMapper();
        mapper.addMappings(new PropertyMap<Currency, CurrencyDTO>() {
            @Override
            protected void configure() {
                map().setName(source.getFullName());
            }
        });
        return mapper.map(currencies, new TypeToken<List<CurrencyDTO>>() {
        }.getType());
    }
}

