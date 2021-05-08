package com.task.exchangerates.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "history_of_calls")
public class Call {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "call_id")
    private Long callId;

    private String date;

    private String description;

    public Call() { }

    public Call(String date, String description) {
        this.date = date;
        this.description = description;
    }

    public Long getCallId() {
        return callId;
    }

    public void setCallId(Long callId) {
        this.callId = callId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
