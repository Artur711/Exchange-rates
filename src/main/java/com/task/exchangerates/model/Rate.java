package com.task.exchangerates.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rate {

    @JsonSetter("currency")
    private String currency;

    @JsonSetter("code")
    private String code;

    @JsonSetter("mid")
    private double mid;

    public String getCurrency() {
        return currency;
    }

    public String getCode() {
        return code;
    }

    public double getMid() {
        return mid;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMid(double mid) {
        this.mid = mid;
    }
}
