package com.weilai.sqlite.controller;

import com.weilai.sqlite.entity.User;
import com.weilai.sqlite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/save-user")
    public User saveUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}
