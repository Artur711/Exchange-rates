package com.task.exchangerates.dto;

public class RateDto {

    private final String currency;

    private final String code;

    private final double mid;

    public RateDto(String currency, String code, double mid) {
        this.currency = currency;
        this.code = code;
        this.mid = mid;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCode() {
        return code;
    }

    public double getMid() {
        return mid;
    }
}
