package com.task.exchangerates.dto;

public class CurrencyDto {

    private String name;

    private String code;

    public CurrencyDto(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
