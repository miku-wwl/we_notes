package com.weilai.h2.service;

import com.weilai.h2.entity.Student;
import com.weilai.h2.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    // 注入Repository（构造方法注入，Spring Boot 3推荐）
    private final StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // ------------ 简单增删改查 ------------
    // 新增/更新学生（save()：id为空则新增，有id则更新）
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    // 根据ID查询
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // 查询所有学生
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // 根据ID删除
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }


    // ------------ 复杂查询 ------------
    // 模糊查询姓名
    public List<Student> searchStudentsByName(String name) {
        return studentRepository.findByNameContaining(name);
    }

    // 年龄范围查询
    public List<Student> searchStudentsByAgeRange(Integer minAge, Integer maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    // 自定义JPQL：年龄大于x且生日在y之后
    public List<Student> searchOlderAndBirthAfter(Integer age, LocalDate birthDay) {
        return studentRepository.findOlderThanAndBirthAfter(age, birthDay);
    }

    // 分页查询（按姓名模糊匹配，每页5条，按年龄降序）
    public Page<Student> searchStudentsByNameWithPage(String name, Integer pageNum) {
        // 排序规则：按age降序（从大到小）
        Sort sort = Sort.by(Sort.Direction.DESC, "age");
        // 分页参数：pageNum（从0开始），每页5条
        Pageable pageable = PageRequest.of(pageNum, 5, sort);
        return studentRepository.findByNameContaining(name, pageable);
    }
}
