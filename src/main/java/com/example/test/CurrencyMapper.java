package com.example.test;

import com.example.dto.CurrencyDTO;
import com.example.entity.Currency;
import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
    }

    public List<CurrencyDTO> toDtoList(List<Currency> currencies) { //ToDo изучить как работает этот код или придумать другой способ
//        List<CurrencyDTO> currencyDTOList = new ArrayList<>();
        ModelMapper mapper = getMapper();
//        TypeMap<Currency, CurrencyDTO> propertyMapper = mapper.createTypeMap(Currency.class, CurrencyDTO.class);

        mapper.addMappings(new PropertyMap<Currency, CurrencyDTO>() {
            @Override
            protected void configure() {
                map().setName(source.getFullName());
            }
        });
        Type listType = new TypeToken<List<CurrencyDTO>>() {}.getType();

//        Converter<List<Currency>, List<CurrencyDTO>> currenciesConverter = c -> c.getSource().retainAll(currencyDTOList);

        return mapper.map(currencies, listType);
    }

    public List<CurrencyDTO> toDtoListTest(List<Currency> currencies) {
        ModelMapper mapper = getMapper();
        Converter<Currency, CurrencyDTO> converter = new Converter<Currency, CurrencyDTO>() {
            @Override
            public CurrencyDTO convert(MappingContext<Currency, CurrencyDTO> context) {
                Currency source = context.getSource();

                List<Currency> sourceCode = currencies;
                List<CurrencyDTO> currencyDTOList = new ArrayList<>();

                for (Currency currency : sourceCode) {
                    currencyDTOList.add(new CurrencyDTO(currency.getId(), currency.getFullName(), currency.getCode(), currency.getSign()));
                }

                return (CurrencyDTO) currencyDTOList;

            }
        };
        return (List<CurrencyDTO>) mapper.createTypeMap(Currency.class, CurrencyDTO.class).setConverter(converter);
    }
}

