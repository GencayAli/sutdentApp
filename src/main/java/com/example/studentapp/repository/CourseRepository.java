package com.example.studentapp.repository;

import com.example.studentapp.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByName(String name);

    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.students")
    List<Course> findAllWithStudents();

    boolean existsByName(String name);

}
