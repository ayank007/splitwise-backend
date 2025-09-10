package com.ayankoley.splitwise.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="SW_User")
public class User extends BaseModel {
    private String name;
    private String email;
    private String password;
}
