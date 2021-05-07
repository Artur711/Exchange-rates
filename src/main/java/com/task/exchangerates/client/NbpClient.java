package com.task.exchangerates.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.exchangerates.entity.Table;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class NbpClient {

    private final HttpClient httpClient;
    private final String API_URL = "http://api.nbp.pl/api/exchangerates/";

    public NbpClient() {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    public Table getSample(String table) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(API_URL + "tables/" + table))
                .headers(HttpHeaders.ACCEPT, "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return deserializeList(response.body());
    }

    private <T> T deserialize(String body, Class<T> tClass) throws JsonProcessingException {
        return new ObjectMapper().readValue(body, tClass);
    }

    private Table deserializeList(String body) throws JsonProcessingException {
        List<Table> ts = new ObjectMapper().readValue(body, new TypeReference<>() {});
        return (ts.size() > 0) ? ts.get(0) : new Table();
    }
}
