package com.weilai.h2webflux.entity;



import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("t_student")  // 对应数据库表名
public class Student {
    @Id  // 主键
    private Long id;

    private String name;

    private String email;

    private Integer age;

    @Column("birth_day")  // 数据库字段名
    private LocalDate birthDay;

    public Student() {}

    public Student(String name, String email, Integer age, LocalDate birthDay) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.birthDay = birthDay;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public LocalDate getBirthDay() { return birthDay; }
    public void setBirthDay(LocalDate birthDay) { this.birthDay = birthDay; }
}
