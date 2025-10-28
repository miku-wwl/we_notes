package com.weilai.h2.repository;

import com.weilai.h2.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // ------------ 简单查询（方法名自动生成SQL）------------
    // 根据姓名模糊查询（like %name%）
    List<Student> findByNameContaining(String name);  // Containing等价于like

    // 根据年龄范围查询（age >= min and age <= max）
    List<Student> findByAgeBetween(Integer minAge, Integer maxAge);

    // 根据邮箱精确查询（返回Optional，避免空指针）
    Optional<Student> findByEmail(String email);


    // ------------ 复杂查询（自定义JPQL）------------
    // JPQL：基于实体类（Student）和属性名，不是表名和字段名
    @Query("SELECT s FROM Student s WHERE s.age > :age AND s.birthDay > :birthDay")
    List<Student> findOlderThanAndBirthAfter(
            @Param("age") Integer age,
            @Param("birthDay") LocalDate birthDay
    );


    // ------------ 复杂查询（原生SQL）------------
    // 原生SQL：直接操作数据库表（t_student）和字段（birth_day）
    @Query(value = "SELECT * FROM t_student WHERE birth_day BETWEEN :start AND :end", nativeQuery = true)
    List<Student> findByBirthDayBetween(
            @Param("start") String startDate,
            @Param("end") String endDate
    );


    // ------------ 分页查询 ------------
    // 按姓名分页查询（Pageable包含页码、每页条数、排序）
    Page<Student> findByNameContaining(String name, Pageable pageable);
}