package com.example.security2315casa.controllers;

import com.example.security2315casa.modelo.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@RestController
//@RequestMapping("managingStudents")
public class StudentManagementController {
    private List<Student> STUDENTS = new ArrayList<>();

    @PostConstruct
    public void fillStudents() {
        STUDENTS.add(new Student(1, "Felipe"));
        STUDENTS.add(new Student(2, "Arturo"));
    }

    @GetMapping
    public List<Student> getStudents() {
        return STUDENTS;
    }

    @PostMapping
    public void addStudent(@RequestBody Student student) {
        System.err.println("entrando en add");
        boolean add = STUDENTS.add(student);

    }

    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable("id") Integer student) {
        STUDENTS.remove(student);
    }

    @PutMapping("{id}")
    public void updateStudent(@RequestBody Student student, @PathVariable("id") Integer id) {
        System.out.println(student);
    }
}
