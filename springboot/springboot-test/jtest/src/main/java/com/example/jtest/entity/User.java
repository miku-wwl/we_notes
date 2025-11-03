package com.example.jtest.entity;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDate;

@Entity
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate birthday; // 用于计算年龄的纯逻辑字段

    // 无参构造器（JPA必需）
    public User() {}

    // 带参构造器（测试用）
    public User(String name, LocalDate birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    // getter、setter（省略，实际开发需生成）
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }
}