package com.ayankoley.splitwise.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="SW_Transaction")
public class Transaction extends BaseModel {
    @ManyToOne
    private User Sender;

    @ManyToOne
    private User Receiver;

    private double amount;

    @ManyToOne
    private Group group;
}
