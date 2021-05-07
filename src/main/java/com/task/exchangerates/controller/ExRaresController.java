package com.task.exchangerates.controller;

import com.task.exchangerates.service.ExRaresService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exchange-rates")
public class ExRaresController {

    private final ExRaresService exRaresService;

    public ExRaresController(ExRaresService exRaresService) {
        this.exRaresService = exRaresService;
    }
}
