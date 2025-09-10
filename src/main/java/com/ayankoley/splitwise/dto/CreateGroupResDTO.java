package com.ayankoley.splitwise.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateGroupResDTO {
    private int groupId;
    private String groupName;
    private String adminName;
    private List<String> members;
}
