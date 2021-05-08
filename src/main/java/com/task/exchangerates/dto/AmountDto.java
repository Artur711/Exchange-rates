package com.task.exchangerates.dto;

import com.task.exchangerates.util.enums.Currency;

public class AmountDto {

    private final double amount;

    private final Currency currency;

    public AmountDto(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

}
