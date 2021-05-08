package com.task.exchangerates.util.converter;

import com.task.exchangerates.dto.RateDto;
import com.task.exchangerates.entity.Rate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RateToDRateDtoConverter {

    public RateDto convert(Rate rate) {
        return new RateDto(rate.getCurrency(), rate.getCode(), rate.getMid());
    }

    public List<RateDto> convertAll(List<Rate> rates) {
        return rates.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
