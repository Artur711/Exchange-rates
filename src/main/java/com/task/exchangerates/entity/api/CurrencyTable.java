package com.task.exchangerates.entity.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyTable {

    @JsonSetter("code")
    private String code;

    @JsonSetter("rates")
    private List<CurrencyRate> currencyRates;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<CurrencyRate> getCurrencyRates() {
        return currencyRates;
    }

    public void setCurrencyRates(List<CurrencyRate> currencyRates) {
        this.currencyRates = currencyRates;
    }

}
