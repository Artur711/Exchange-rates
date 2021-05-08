package com.task.exchangerates.dto;

import com.task.exchangerates.util.enums.Currency;

public class ExchangeDto {

    private final double amount;

    private final Currency exchangeFrom;

    private final Currency exchangeTo;

    public ExchangeDto(double amount, Currency exchangeFrom, Currency exchangeTo) {
        this.amount = amount;
        this.exchangeFrom = exchangeFrom;
        this.exchangeTo = exchangeTo;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getExchangeFrom() {
        return exchangeFrom;
    }

    public Currency getExchangeTo() {
        return exchangeTo;
    }
}
