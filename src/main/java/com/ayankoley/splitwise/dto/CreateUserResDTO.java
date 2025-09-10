package com.ayankoley.splitwise.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserResDTO {
    private int userId;
    private String name;
    private String email;
}
