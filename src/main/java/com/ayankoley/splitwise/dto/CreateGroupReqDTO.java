package com.ayankoley.splitwise.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateGroupReqDTO {
    private String name;
    private int adminId;
    private List<Integer> memberIds;
}
