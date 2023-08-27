package com.example.test;

import com.example.entity.Currency;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;

import javax.print.attribute.standard.Destination;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.Objects;

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

    public CurrencyDTO toDto(Currency currency) {
        ModelMapper mapper = getMapper();
        TypeMap<Currency, CurrencyDTO> propertyMapper = mapper.createTypeMap(Currency.class, CurrencyDTO.class);
        propertyMapper.addMapping(Currency::getFullName, CurrencyDTO::setName);
        return mapper.map(currency, CurrencyDTO.class);

//        mapper.addMappings(new PropertyMap<Currency, CurrencyDTO>() {
//            protected void configure() {
//                map().setName(source.getFullName());
//            }
//        });
//        return mapper.map(currency, CurrencyDTO.class);
    }
}
