package com.task.exchangerates.controller;

import com.task.exchangerates.service.ExRaresService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String getSample(@RequestParam(required = false) String table) throws
            URISyntaxException, IOException, InterruptedException {
        return (table == null) ? exRaresService.getSample("A") : exRaresService.getSample(table.toUpperCase());
    }
}
