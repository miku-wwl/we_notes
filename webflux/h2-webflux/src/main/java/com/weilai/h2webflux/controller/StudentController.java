package com.weilai.h2webflux.controller;


import com.weilai.h2webflux.entity.Student;
import com.weilai.h2webflux.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 新增学生
    @PostMapping
    public Mono<ResponseEntity<Student>> addStudent(@RequestBody Student student) {
        return studentService.saveStudent(student)
                .map(saved -> new ResponseEntity<>(saved, HttpStatus.CREATED));
    }

    // 根据ID查询
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Student>> getStudent(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // 查询所有
    @GetMapping
    public Flux<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // 更新学生
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Student>> updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {
        return studentService.getStudentById(id)
                .flatMap(existing -> {
                    student.setId(id);
                    return studentService.saveStudent(student);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // 删除学生
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    // 模糊查询姓名
    @GetMapping("/search/name")
    public Flux<Student> searchByName(@RequestParam String name) {
        return studentService.searchStudentsByName(name);
    }

    // 年龄范围查询（补充异常处理）
    @GetMapping("/search/age")
    public Flux<Student> searchByAge(
            @RequestParam Integer min,
            @RequestParam Integer max) {
        return studentService.searchStudentsByAgeRange(min, max)
                .onErrorResume(IllegalArgumentException.class, e ->
                        Flux.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage()))
                );
    }

    // 新增：分页查询姓名
    @GetMapping("/search/name/page")
    public Flux<Student> searchByNameWithPage(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") Integer pageNum) {
        return studentService.searchStudentsByNameWithPage(name, pageNum)
                .onErrorResume(IllegalArgumentException.class, e ->
                        Flux.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage()))
                );
    }

    // 新增：年龄大于指定值且生日在指定日期之后的学生
    @GetMapping("/search/older")
    public Flux<Student> searchOlderThan(
            @RequestParam Integer age) {
        return studentService.searchOlderThan(age)
                .onErrorResume(IllegalArgumentException.class, e ->
                        Flux.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage()))
                );
    }
}
