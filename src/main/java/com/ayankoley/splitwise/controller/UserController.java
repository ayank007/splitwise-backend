package com.ayankoley.splitwise.controller;

import com.ayankoley.splitwise.dto.CreateUserReqDTO;
import com.ayankoley.splitwise.dto.CreateUserResDTO;
import com.ayankoley.splitwise.mapper.UserDTOMapper;
import com.ayankoley.splitwise.model.User;
import com.ayankoley.splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<CreateUserResDTO> createUser(@RequestBody CreateUserReqDTO createUserReqDTO) {
        User newUser = UserDTOMapper.convertDTOToEntity(createUserReqDTO);
        newUser = userService.save(newUser);

        CreateUserResDTO createUserRes = UserDTOMapper.convertEntityToDTO(newUser);

        return ResponseEntity.ok(createUserRes);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUser(@PathVariable int userId) {
        User user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }
}
