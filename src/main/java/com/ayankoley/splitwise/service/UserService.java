package com.ayankoley.splitwise.service;

import com.ayankoley.splitwise.model.User;
import com.ayankoley.splitwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findById(int id) {
        return userRepository
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );
    }

    public User login(String email, String password) {
        User savedUser = userRepository.findByEmail(email);
        if (savedUser.getPassword().equals(password)) {
            return savedUser;
        } else {
            return null;
        }
    }

}
