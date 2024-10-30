package com.weilai.sqlite.controller;

import com.weilai.sqlite.entity.User;
import com.weilai.sqlite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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


    @PostMapping("/transactional")
    @Transactional
    public void createUserWithException(@RequestBody User user) {
        userRepository.save(user);
        if (user.getAge() < 18) {
            throw new RuntimeException("Age should be at least 18.");
        }
        // 这一行不会被执行，因为前面抛出了异常
        userRepository.save(user);
    }
}
