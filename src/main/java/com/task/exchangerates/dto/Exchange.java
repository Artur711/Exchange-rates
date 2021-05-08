package com.task.exchangerates.dto;

import com.task.exchangerates.util.Currency;

public class Exchange {

    private double amount;

    private Currency exchangeFrom;

    private Currency exchangeTo;

    public Exchange() {}

    public Exchange(double amount, Currency exchangeFrom, Currency exchangeTo) {
        this.amount = amount;
        this.exchangeFrom = exchangeFrom;
        this.exchangeTo = exchangeTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Currency getExchangeFrom() {
        return exchangeFrom;
    }

    public void setExchangeFrom(Currency exchangeFrom) {
        this.exchangeFrom = exchangeFrom;
    }

    public Currency getExchangeTo() {
        return exchangeTo;
    }

    public void setExchangeTo(Currency exchangeTo) {
        this.exchangeTo = exchangeTo;
    }
}
