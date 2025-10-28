package com.weilai.h2webflux.service;

import com.weilai.h2webflux.entity.Student;
import com.weilai.h2webflux.repository.StudentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // 构造方法注入
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // 新增/更新学生（返回Mono<Student>）
    public Mono<Student> saveStudent(Student student) {
        return studentRepository.save(student);  // R2DBC的save是响应式方法
    }

    // 根据ID查询（返回Mono<Student>，不存在则返回空Mono）
    public Mono<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // 查询所有学生（返回Flux<Student>）
    public Flux<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // 根据ID删除（返回Mono<Void>，表示完成信号）
    public Mono<Void> deleteStudent(Long id) {
        return studentRepository.deleteById(id);
    }

    // 模糊查询姓名
    public Flux<Student> searchStudentsByName(String name) {
        return studentRepository.findByNameContaining(name);
    }

    // 年龄范围查询（补充参数校验）
    public Flux<Student> searchStudentsByAgeRange(Integer minAge, Integer maxAge) {
        if (minAge == null || maxAge == null || minAge > maxAge) {
            return Flux.error(new IllegalArgumentException("年龄参数错误：minAge必须<=maxAge且不为空"));
        }
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    // 分页查询姓名（补充页码校验）
    public Flux<Student> searchStudentsByNameWithPage(String name, Integer pageNum) {
        if (pageNum < 0) {
            return Flux.error(new IllegalArgumentException("页码不能为负数"));
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "age");
        Pageable pageable = PageRequest.of(pageNum, 5, sort); // 每页5条，按年龄倒序
        return studentRepository.findByNameContaining(name)
                .skip((long) pageable.getPageNumber() * pageable.getPageSize())
                .take(pageable.getPageSize());
    }

    // 新增：年龄大于指定值且生日在指定日期之后的学生
    public Flux<Student> searchOlderThan(Integer age) {
        if (age == null) {
            return Flux.error(new IllegalArgumentException("年龄和生日参数不能为空"));
        }
        return studentRepository.findOlderThan(age);
    }
}
