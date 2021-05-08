package com.task.exchangerates.controller;

import com.task.exchangerates.dto.AmountDto;
import com.task.exchangerates.dto.CurrencyDto;
import com.task.exchangerates.dto.ExchangeDto;
import com.task.exchangerates.dto.RateDto;
import com.task.exchangerates.service.ExRaresService;
import com.task.exchangerates.util.enums.Currency;
import com.task.exchangerates.util.converter.CurrencyToCurrencyDtoConverter;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/exchange-rates")
public class ExRaresController {

    private final ExRaresService exRaresService;
    private final CurrencyToCurrencyDtoConverter converter;

    public ExRaresController(ExRaresService exRaresService, CurrencyToCurrencyDtoConverter converter) {
        this.exRaresService = exRaresService;
        this.converter = converter;
    }

    @GetMapping
    @ApiOperation(value = "List of currencies available for conversion")
    public List<CurrencyDto> getRates() {
        return converter.convertAll(Arrays.asList(Currency.values()));
    }

    @GetMapping("/exchange")
    @ApiOperation(value = "Currency conversion to another")
    public AmountDto exchange(@RequestBody ExchangeDto exchange) throws
            URISyntaxException, IOException, InterruptedException {

        return (exchange.getExchangeFrom().equals(exchange.getExchangeTo())) ?
                new AmountDto(exchange.getAmount(), exchange.getExchangeTo()) :
                exRaresService.exchangeRates(exchange);
    }

    @GetMapping("/list")
    @ApiOperation(value = "List of currencies with rates")
    public List<RateDto> getRatesList() throws URISyntaxException, IOException, InterruptedException {
        return exRaresService.getRates();
    }
}
