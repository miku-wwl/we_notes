package com.weilai.h2webflux.repository;

import com.weilai.h2webflux.entity.Student;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface StudentRepository extends R2dbcRepository<Student, Long> {

    // 响应式查询：根据姓名模糊查询（返回多个结果，用Flux）
    Flux<Student> findByNameContaining(String name);

    // 响应式查询：根据年龄范围查询
    Flux<Student> findByAgeBetween(Integer minAge, Integer maxAge);

    // 自定义响应式查询（JPQL风格，基于实体类）
    @Query("SELECT * FROM t_student WHERE age > :age")
    Flux<Student> findOlderThan(
            @Param("age") Integer age
    );
}