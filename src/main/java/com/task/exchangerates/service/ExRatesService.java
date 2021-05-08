package com.task.exchangerates.service;

import com.task.exchangerates.client.NbpClient;
import com.task.exchangerates.dto.AmountDto;
import com.task.exchangerates.dto.ExchangeDto;
import com.task.exchangerates.dto.RateDto;
import com.task.exchangerates.model.entity.Call;
import com.task.exchangerates.repository.ExRatesRepository;
import com.task.exchangerates.util.converter.RateToDRateDtoConverter;
import com.task.exchangerates.util.enums.Currency;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ExRatesService {

    private final NbpClient nbpClient;
    private final RateToDRateDtoConverter rateDtoConverter;
    private final ExRatesRepository repository;

    public ExRatesService(NbpClient nbpClient,
                          RateToDRateDtoConverter rateDtoConverter,
                          ExRatesRepository repository) {
        this.nbpClient = nbpClient;
        this.rateDtoConverter = rateDtoConverter;
        this.repository = repository;
    }

    public AmountDto exchangeRates(ExchangeDto exchange) throws
            URISyntaxException, IOException, InterruptedException {

        double rateFrom = nbpClient.getRateCurrency(exchange.getExchangeFrom().toString());
        addTextAsCall(formatCollectionOfTheRate(exchange.getExchangeFrom(), rateFrom));
        double rateTo = nbpClient.getRateCurrency(exchange.getExchangeTo().toString());
        addTextAsCall(formatCollectionOfTheRate(exchange.getExchangeTo(), rateTo));

        if (rateTo != 0) {
            addTextAsCall(String.format("Converted %s amount form %s to %s.",
                    exchange.getAmount(), exchange.getExchangeFrom(), exchange.getExchangeTo()));

            double result = exchangeRatesResult(exchange.getAmount(), rateFrom, rateTo);

            return new AmountDto(result, exchange.getExchangeTo());
        }
        addTextAsCall(String.format("Converted from %s to %s failed.",
                exchange.getExchangeFrom(), exchange.getExchangeTo()));

        return new AmountDto(0, exchange.getExchangeTo());
    }

    public List<RateDto> getRates() throws URISyntaxException, IOException, InterruptedException {
        List<RateDto> ratesDto = rateDtoConverter.convertAll(nbpClient.getRates().getRates());
        addTextAsCall("Downloading currency rates.");
        List<String> currencies = Stream.of(Currency.values())
                .map(Currency::toString)
                .collect(Collectors.toList());

        addTextAsCall("Filter available rates of currencies.");
        return ratesDto.stream()
                .filter(rateDto -> currencies.contains(rateDto.getCode()))
                .collect(Collectors.toList());
    }

    public void addTextAsCall(String text) {
        repository.save(new Call(getCurrentDateAsString(), text));
    }

    private double exchangeRatesResult(double amount, double rateFrom, double rateTo) {
        BigDecimal amountBig = BigDecimal.valueOf(amount);
        BigDecimal result = amountBig.multiply(BigDecimal.valueOf(rateFrom / rateTo));
        result = result.setScale(4, RoundingMode.HALF_UP);

        return result.doubleValue();
    }

    private String getCurrentDateAsString() {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());

        return formatter.format(date);
    }

    private String formatCollectionOfTheRate(Currency currency, double rate){
        return String.format("Collection of the %s rate (%s).", currency, rate);
    }
}
