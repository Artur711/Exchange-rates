package com.task.exchangerates.controller;

import com.task.exchangerates.entity.Amount;
import com.task.exchangerates.entity.Exchange;
import com.task.exchangerates.entity.api.Table;
import com.task.exchangerates.service.ExRaresService;
import com.task.exchangerates.util.Currency;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/exchange-rates")
public class ExRaresController {

    private final ExRaresService exRaresService;

    public ExRaresController(ExRaresService exRaresService) {
        this.exRaresService = exRaresService;
    }

    @GetMapping
    public Table getRates() throws
            URISyntaxException, IOException, InterruptedException {
        return exRaresService.getRates();
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
