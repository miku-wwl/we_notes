package com.imooc.log.stack.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Imoocer {

    /** 唯一标识 */
    private Long id;

    /** 姓名 */
    private String name;

    /** 年龄 */
    private Integer age;

    /** 工资 */
    private Double salary;

    /** 头像 */
    private String profile;

    /** 正在学习的课程 */
    private List<Course> courses;

    public Imoocer(String name, Integer age, Double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    /**
     * <h2>课程</h2>
     * */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Course {

        /** 课程名 */
        private String courseName;

        /** 课程讲师 */
        private String teacher;
    }

    /**
     * <h2>构造一个默认的 Imoocer</h2>
     * */
    public static Imoocer defaultObj() {

        List<Course> courses = Arrays.asList(
                new Course("广告", "勤一"),
                new Course("优惠券", "勤一")
        );

        return new Imoocer(
                -1L, "勤一", 19, 1000.0, "fangao.jpg", courses
        );
    }
}
