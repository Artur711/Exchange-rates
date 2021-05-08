package com.task.exchangerates.util.enums;

public enum Currency {

    PLN("złotówka polska"),
    EUR("euro"),
    USD("dolar amerykański"),
    GBP("funt szterling"),
    CHF("frank szwajcarski"),
    RUB("rubel rosyjski"),
    CNY("yuan renminbi (Chiny)"),
    TRY("lira turecka"),
    SEK("korona szwedzka");

    private final String name;

    Currency(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
