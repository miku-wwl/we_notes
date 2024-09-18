package com.weilai.learnjpa2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private int age;
    private String sex;
    private String email;

    // 与 Address 的关联
    private Long addressId;


    public UserInfo(String name, int age, String sex, String email, Long addressId) {
        super();
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.email = email;
        this.addressId = addressId;
    }
}