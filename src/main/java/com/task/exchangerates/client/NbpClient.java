package com.task.exchangerates.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.exchangerates.entity.CurrencyTable;
import com.task.exchangerates.entity.Table;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class NbpClient {

    private final HttpClient httpClient;
    private final String API_URL = "http://api.nbp.pl/api/exchangerates";

    public NbpClient() {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    public double getRateCurrency(String code) throws URISyntaxException, IOException, InterruptedException {
        return  (code.equals("PLN") ? 1 : getRate(code));
    }

    public Table getRates() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(String.format("%s/tables/A", API_URL)))
                .headers(HttpHeaders.ACCEPT, "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return deserializeList(response.body());
    }

    private double getRate(String code) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(String.format("%s/rates/A/%s", API_URL, code)))
                .headers(HttpHeaders.ACCEPT, "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        CurrencyTable currency = deserialize(response.body());
        return (currency.getCurrencyRates().size() > 0) ? currency.getCurrencyRates().get(0).getMid() : 1;
    }

    private CurrencyTable deserialize(String body) throws JsonProcessingException {
        return new ObjectMapper().readValue(body, CurrencyTable.class);
    }

    private Table deserializeList(String body) throws JsonProcessingException {
        List<Table> ts = new ObjectMapper().readValue(body, new TypeReference<>() {});
        return (ts.size() > 0) ? ts.get(0) : new Table();
    }
}
