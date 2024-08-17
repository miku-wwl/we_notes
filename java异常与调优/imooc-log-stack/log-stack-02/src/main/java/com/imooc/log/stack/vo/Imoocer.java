package com.imooc.log.stack.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Imoocer {

    private String name;
    private Integer age;
    private Double salary;

    public Imoocer(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
