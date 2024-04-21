package com.fetch.demofetch.controller;

import com.fetch.demofetch.model.Student;
import com.fetch.demofetch.model.User;
import com.fetch.demofetch.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<Student> register(@ModelAttribute Student student) throws IOException {
        student.setPhoto(studentService.makeBlob(student.getFile()));
        return ResponseEntity.ok(studentService.register(student));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Student>> findAll(){
        return ResponseEntity.ok(studentService.findAll());
    }
}
