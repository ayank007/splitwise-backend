package com.ayankoley.splitwise.dto;

import com.ayankoley.splitwise.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserExpenseEntry {
    private User user;
    private double amount;

    public UserExpenseEntry(User user, double amount) {
        this.user = user;
        this.amount = amount;
    }
}
