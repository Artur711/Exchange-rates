package com.task.exchangerates.controller;

import com.task.exchangerates.dto.Amount;
import com.task.exchangerates.dto.CurrencyDto;
import com.task.exchangerates.dto.Exchange;
import com.task.exchangerates.entity.Table;
import com.task.exchangerates.service.ExRaresService;
import com.task.exchangerates.util.Currency;
import com.task.exchangerates.util.CurrencyToCurrencyDtoConverter;
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
    public List<CurrencyDto> getRates() {
        return converter.convertAll(Arrays.asList(Currency.values()));
    }

    @GetMapping("/exchange")
    public Amount exchange(@RequestBody Exchange exchange) throws
            URISyntaxException, IOException, InterruptedException {

        return (exchange.getExchangeFrom().equals(exchange.getExchangeTo())) ?
                new Amount(exchange.getAmount(), exchange.getExchangeTo()) :
                exRaresService.exchangeRates(exchange);
    }

    @GetMapping("/list")
    public Table getRatesList() throws URISyntaxException, IOException, InterruptedException {
        return exRaresService.getRates();
    }

    @GetMapping("/exchange/sample")
    public Exchange exchangeSample() {
        return new Exchange(1068.3, Currency.EUR, Currency.USD);
    }
}
