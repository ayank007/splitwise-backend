package com.ayankoley.splitwise.mapper;

import com.ayankoley.splitwise.dto.CreateUserReqDTO;
import com.ayankoley.splitwise.dto.CreateUserResDTO;
import com.ayankoley.splitwise.model.User;

public class UserDTOMapper {

    public static User convertDTOToEntity (CreateUserReqDTO createUserReqDTO) {
        User user = new User();
        user.setName(createUserReqDTO.getName());
        user.setEmail(createUserReqDTO.getEmail());
        user.setPassword(createUserReqDTO.getPassword());

        return user;
    }

    public static CreateUserResDTO convertEntityToDTO(User user) {
        CreateUserResDTO createUserResDTO = new CreateUserResDTO();
        createUserResDTO.setUserId(user.getId());
        createUserResDTO.setName(user.getName());
        createUserResDTO.setEmail(user.getEmail());

        return createUserResDTO;
    }
}
