package com.example.studentapp.service;

import com.example.studentapp.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> getAllStudents();

    void save(Student student);

    Optional<Student> findById(Long id);

    Student findByEmail(String email);
}
