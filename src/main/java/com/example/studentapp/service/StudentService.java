package com.example.studentapp.service;

import com.example.studentapp.model.Student;
import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    void saveStudent(Student student);


}


