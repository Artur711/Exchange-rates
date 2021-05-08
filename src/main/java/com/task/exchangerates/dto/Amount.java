package com.task.exchangerates.dto;

import com.task.exchangerates.util.Currency;

public class Amount {

    private double amount;

    private Currency currency;

    public Amount() {};

    public Amount(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
