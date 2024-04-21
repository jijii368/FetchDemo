package com.fetch.demofetch.service;

import com.fetch.demofetch.model.Student;
import com.fetch.demofetch.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class StudentService  {

    @Autowired
    StudentRepo studentRepo;

    public Student register(Student student){
        return studentRepo.save(student);
    }

    public byte[] makeBlob(MultipartFile multipartFile) throws IOException {
        return new ByteArrayInputStream(multipartFile.getBytes()).readAllBytes();
    }

    public List<Student> findAll() {
        return studentRepo.findAll();
    }
}
