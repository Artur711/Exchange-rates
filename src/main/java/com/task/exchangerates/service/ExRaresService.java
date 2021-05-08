package com.task.exchangerates.service;

import com.task.exchangerates.client.NbpClient;
import com.task.exchangerates.dto.Amount;
import com.task.exchangerates.dto.Exchange;
import com.task.exchangerates.entity.Table;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;

@Service
public class ExRaresService {

    private final NbpClient nbpClient;

    public ExRaresService(NbpClient nbpClient) {
        this.nbpClient = nbpClient;
    }

    public Amount exchangeRates(Exchange exchange) throws URISyntaxException, IOException, InterruptedException {
        double rateFrom = nbpClient.getRateCurrency(exchange.getExchangeFrom().toString());
        double rateTo = nbpClient.getRateCurrency(exchange.getExchangeTo().toString());

        if (rateTo != 0) {
            double result = exchangeRatesResult(exchange.getAmount(), rateFrom, rateTo);
            return new Amount(result, exchange.getExchangeTo());
        }
        return new Amount(0, exchange.getExchangeTo());
    }

    public Table getRates() throws URISyntaxException, IOException, InterruptedException {
        return nbpClient.getRates();
    }

    private double exchangeRatesResult(double amount, double rateFrom, double rateTo) {
        BigDecimal amountBig = BigDecimal.valueOf(amount);
        BigDecimal result = amountBig.multiply(BigDecimal.valueOf(rateFrom / rateTo));
        result = result.setScale(4, RoundingMode.HALF_UP);

        return result.doubleValue();
    }
}
