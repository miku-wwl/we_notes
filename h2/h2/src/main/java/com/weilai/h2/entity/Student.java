package com.weilai.h2.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "t_student")  // 数据库表名
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 自增主键
    private Long id;

    @Column(nullable = false, length = 100)  // 非空，最长100字符
    private String name;

    @Column(unique = true, nullable = false)  // 邮箱唯一且非空
    private String email;

    private Integer age;

    @Column(name = "birth_day")  // 数据库字段名：birth_day
    private LocalDate birthDay;  // 生日（LocalDate是JDK 8+的日期类型，推荐使用）

    // 无参构造（JPA必须）
    public Student() {}

    // 有参构造（方便创建对象）
    public Student(String name, String email, Integer age, LocalDate birthDay) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.birthDay = birthDay;
    }

    // Getter和Setter（必须，JPA需要通过getter访问字段）
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

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", birthDay=" + birthDay +
                '}';
    }
}