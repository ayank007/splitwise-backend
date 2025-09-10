package com.ayankoley.splitwise.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserExpenseReqDTO {
    private int userId;
    private double amount;
    private int expenseType;
}
