package com.task.exchangerates.service;

import com.task.exchangerates.client.NbpClient;
import com.task.exchangerates.dto.AmountDto;
import com.task.exchangerates.dto.ExchangeDto;
import com.task.exchangerates.dto.RateDto;
import com.task.exchangerates.util.converter.RateToDRateDtoConverter;
import com.task.exchangerates.util.enums.Currency;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ExRaresService {

    private final NbpClient nbpClient;

    private final RateToDRateDtoConverter rateDtoConverter;

    public ExRaresService(NbpClient nbpClient, RateToDRateDtoConverter rateDtoConverter) {
        this.nbpClient = nbpClient;
        this.rateDtoConverter = rateDtoConverter;
    }

    public AmountDto exchangeRates(ExchangeDto exchange) throws URISyntaxException, IOException, InterruptedException {
        double rateFrom = nbpClient.getRateCurrency(exchange.getExchangeFrom().toString());
        double rateTo = nbpClient.getRateCurrency(exchange.getExchangeTo().toString());

        if (rateTo != 0) {
            double result = exchangeRatesResult(exchange.getAmount(), rateFrom, rateTo);
            return new AmountDto(result, exchange.getExchangeTo());
        }
        return new AmountDto(0, exchange.getExchangeTo());
    }

    public List<RateDto> getRates() throws URISyntaxException, IOException, InterruptedException {
        List<RateDto> ratesDto = rateDtoConverter.convertAll(nbpClient.getRates().getRates());
        List<String> currencies = Stream.of(Currency.values())
                .map(Currency::toString)
                .collect(Collectors.toList());

        return ratesDto.stream()
                .filter(rateDto -> currencies.contains(rateDto.getCode()))
                .collect(Collectors.toList());
    }

    private double exchangeRatesResult(double amount, double rateFrom, double rateTo) {
        BigDecimal amountBig = BigDecimal.valueOf(amount);
        BigDecimal result = amountBig.multiply(BigDecimal.valueOf(rateFrom / rateTo));
        result = result.setScale(4, RoundingMode.HALF_UP);

        return result.doubleValue();
    }
}
