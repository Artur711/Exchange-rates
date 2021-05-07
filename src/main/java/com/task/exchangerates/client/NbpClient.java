package com.task.exchangerates.client;

import org.springframework.stereotype.Service;

import java.net.http.HttpClient;

@Service
public class NbpClient {

    private final HttpClient httpClient;
    private final String API_URL = "http://api.nbp.pl/api/";

    public NbpClient() {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }


}
