package com.task.exchangerates.service;

import com.task.exchangerates.client.NbpClient;
import com.task.exchangerates.dto.AmountDto;
import com.task.exchangerates.dto.ExchangeDto;
import com.task.exchangerates.dto.RateDto;
import com.task.exchangerates.model.Table;
import com.task.exchangerates.repository.ExRatesRepository;
import com.task.exchangerates.util.converter.RateToDRateDtoConverter;
import com.task.exchangerates.util.enums.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ExRatesServiceTest {

    @Mock
    private ExRatesRepository repository;

    @Mock
    private NbpClient client;

    @Mock
    private RateToDRateDtoConverter converter;

    @InjectMocks
    private ExRatesService service;

    @Mock
    private ExRatesService exRatesService;

    @Test
    void shouldExchangedFromEurToChf() throws URISyntaxException, IOException, InterruptedException {
        // given
        ExchangeDto exchangeDto = new ExchangeDto(1.33, Currency.EUR, Currency.CHF);
        when(client.getRateCurrency(exchangeDto.getExchangeFrom().toString())).thenReturn(3.20);
        when(client.getRateCurrency(exchangeDto.getExchangeTo().toString())).thenReturn(2.20);

        // when
        AmountDto amountDto = service.exchangeRates(exchangeDto);

        // then
        assertAll(() -> assertEquals(1.9345, amountDto.getAmount()),
                () -> assertEquals(Currency.CHF, amountDto.getCurrency()));
    }

    @Test
    void shouldExchangedFromEurToChfTwoTimes() throws URISyntaxException, IOException, InterruptedException {
        // given
        ExchangeDto exchangeDto = new ExchangeDto(107.21, Currency.CHF, Currency.EUR);
        when(client.getRateCurrency(exchangeDto.getExchangeFrom().toString())).thenReturn(2.20);
        when(client.getRateCurrency(exchangeDto.getExchangeTo().toString())).thenReturn(3.20);

        // when
        AmountDto amountDto1 = service.exchangeRates(exchangeDto);
        AmountDto amountDto2 = service.exchangeRates(exchangeDto);

        // then
        assertAll(() -> assertEquals(73.7069, amountDto1.getAmount()),
                () -> assertEquals(Currency.EUR, amountDto1.getCurrency()),
                () -> assertEquals(amountDto1.getAmount(), amountDto2.getAmount()));
    }

    @Test
    void shouldGotRates() throws URISyntaxException, IOException, InterruptedException {
        // given
        Table table = new Table();
        when(client.getRates()).thenReturn(table);
        when(converter.convertAll(table.getRates())).thenReturn(getSamplesRates());

        // when
        List<RateDto> rates = service.getRates();

        // when
        assertEquals(6, rates.size());
    }

    @Test
    void shouldGotRatesThreeTimes() throws URISyntaxException, IOException, InterruptedException {
        // given
        int size = 6;
        Table table = new Table();
        when(client.getRates()).thenReturn(table);
        when(converter.convertAll(table.getRates())).thenReturn(getSamplesRates());

        // when
        List<RateDto> rates1 = service.getRates();
        List<RateDto> rates2 = service.getRates();
        List<RateDto> rates3 = service.getRates();

        // when
        assertAll(() -> assertEquals(size, rates1.size()),
                () -> assertEquals(size, rates2.size()),
                () -> assertEquals(size, rates3.size()));
    }

    private List<RateDto> getSamplesRates() {
        RateDto rate1 = new RateDto("dolar amerykański", "USD", 3.6659);
        RateDto rate2 = new RateDto("euro", "EUR", 4.4654);
        RateDto rate3 = new RateDto("frank szwajcarski", "CHF", 4.0707);
        RateDto rate4 = new RateDto("lira turecka", "TRY", 0.4247);
        RateDto rate5 = new RateDto("rubel rosyjski", "RUB", 0.0499);
        RateDto rate6 = new RateDto("yuan renminbi (Chiny)", "CNY", 0.5742);

        return Arrays.asList(rate1, rate2, rate3, rate4, rate5, rate6);
    }

    @Test
    void shouldAddTextAsCall() {
        // given
        String sample = "Sample";

        // when
        exRatesService.addTextAsCall(sample);

        //then
        verify(exRatesService, times(1)).addTextAsCall(sample);
    }

    @Test
    void shouldAddThreeTimesTextAsCall() {
        // given
        String sample = "Sample 2";

        // when
        for (int i = 0; i < 3; i++)
            exRatesService.addTextAsCall(sample);

        //then
        verify(exRatesService, times(3)).addTextAsCall(sample);
    }

    @Test
    void shouldAddVariousTextsAsCall() {
        // given
        String sample1 = "Sample 1";
        String sample2 = "Sample 2";
        String sample3 = "Sample 3";
        List<String> samples = Arrays.asList(sample1, sample2, sample1, sample3, sample1);

        // when
        for (String sample : samples)
            exRatesService.addTextAsCall(sample);

        //then
        assertAll(() -> verify(exRatesService, times(3)).addTextAsCall(sample1),
                () -> verify(exRatesService, times(1)).addTextAsCall(sample2),
                () -> verify(exRatesService, times(1)).addTextAsCall(sample3));
    }
}
