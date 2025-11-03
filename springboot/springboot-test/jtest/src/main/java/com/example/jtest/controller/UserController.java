package com.example.jtest.controller;

import com.example.jtest.entity.User;
import com.example.jtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/age")
    public ResponseEntity<Integer> calculateAge(
            // 接收请求参数，并指定日期格式（避免解析错误）
            @RequestParam("birthday") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday) {
        int age = userService.calculateAge(birthday);
        return ResponseEntity.ok(age); // 返回200状态码 + 年龄
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        // 返回201状态码（创建成功） + 保存后的用户信息
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user); // 存在：200 + 用户信息
        } else {
            return ResponseEntity.notFound().build(); // 不存在：404
        }
    }
}