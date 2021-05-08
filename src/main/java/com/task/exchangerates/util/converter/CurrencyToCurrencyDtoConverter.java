package com.task.exchangerates.util.converter;

import com.task.exchangerates.dto.CurrencyDto;
import com.task.exchangerates.util.enums.Currency;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CurrencyToCurrencyDtoConverter {

    public CurrencyDto convert(Currency currency) {
        return new CurrencyDto(currency.getName(), currency.toString());
    }

    public List<CurrencyDto> convertAll(List<Currency> currencies) {
        return currencies.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
