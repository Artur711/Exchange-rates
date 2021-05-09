package com.task.exchangerates.controller;

import com.task.exchangerates.dto.AmountDto;
import com.task.exchangerates.dto.CurrencyDto;
import com.task.exchangerates.dto.ExchangeDto;
import com.task.exchangerates.dto.RateDto;
import com.task.exchangerates.service.ExRatesService;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/exchange-rates")
public class ExRatesController {

    private final ExRatesService exRatesService;
    private final CurrencyToCurrencyDtoConverter converter;

    public ExRatesController(ExRatesService exRatesService, CurrencyToCurrencyDtoConverter converter) {
        this.exRatesService = exRatesService;
        this.converter = converter;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List of currencies available for conversion")
    public List<CurrencyDto> getRates() {
        exRatesService.addTextAsCall("Sharing list of available currencies.");
        return converter.convertAll(Arrays.asList(Currency.values()));
    }

    @GetMapping(value = "/exchange", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Currency conversion to another")
    public AmountDto exchange(@RequestBody ExchangeDto exchange) throws
            URISyntaxException, IOException, InterruptedException {

        return (exchange.getExchangeFrom().equals(exchange.getExchangeTo())) ?
                new AmountDto(exchange.getAmount(), exchange.getExchangeTo()) :
                exRatesService.exchangeRates(exchange);
    }

    @GetMapping(value = "/list", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "List of currencies with rates")
    public List<RateDto> getRatesList() throws URISyntaxException, IOException, InterruptedException {
        return exRatesService.getRates();
    }
}
