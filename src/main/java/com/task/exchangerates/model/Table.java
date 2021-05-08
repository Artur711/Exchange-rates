package com.task.exchangerates.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Table {

    @JsonSetter("table")
    private String table;

    @JsonSetter("rates")
    private List<Rate> rates;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }
}
