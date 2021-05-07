package com.task.exchangerates.service;

import com.task.exchangerates.client.NbpClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class ExRaresService {

    private final NbpClient nbpClient;

    public ExRaresService(NbpClient nbpClient) {
        this.nbpClient = nbpClient;
    }

    public String getSample(String table) throws URISyntaxException, IOException, InterruptedException {
        return nbpClient.getSample(table);
    }
}
