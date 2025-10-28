package com.weilai.h2.controller;

import com.weilai.h2.entity.Student;
import com.weilai.h2.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/students")  // 接口统一前缀
public class StudentController {

    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 1. 新增学生（POST）
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        log.info("新增学生：{}", student);
        Student saved = studentService.saveStudent(student);
        return ResponseEntity.ok(saved);  // 返回200 + 保存后的对象
    }

    // 2. 根据ID查询（GET）
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        // 存在则返回200+数据，否则返回404
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. 查询所有（GET）
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // 4. 更新学生（PUT）
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {
        // 先查是否存在
        Optional<Student> existing = studentService.getStudentById(id);
        if (existing.isPresent()) {
            student.setId(id);  // 确保ID一致（防止更新时改ID）
            Student updated = studentService.saveStudent(student);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // 5. 删除学生（DELETE）
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();  // 返回204（无内容）
    }


    // ------------ 复杂查询接口 ------------
    // 模糊查询姓名
    @GetMapping("/search/name")
    public ResponseEntity<List<Student>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(studentService.searchStudentsByName(name));
    }

    // 年龄范围查询
    @GetMapping("/search/age")
    public ResponseEntity<List<Student>> searchByAge(
            @RequestParam Integer min,
            @RequestParam Integer max) {
        return ResponseEntity.ok(studentService.searchStudentsByAgeRange(min, max));
    }

    // 分页查询
    @GetMapping("/search/page")
    public ResponseEntity<Page<Student>> searchByPage(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") Integer pageNum) {  // 默认查第1页（0开始）
        return ResponseEntity.ok(studentService.searchStudentsByNameWithPage(name, pageNum));
    }
}


