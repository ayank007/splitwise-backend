package com.ayankoley.splitwise.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateExpenseReqDTO {
    private String description;
    private int amount;
    private int createdById;
    private List<UserExpenseReqDTO> userExpenses;
}
