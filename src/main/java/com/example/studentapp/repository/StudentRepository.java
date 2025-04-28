package com.example.studentapp.repository;

import com.example.studentapp.model.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    //Student findByEmail(String email);
    List<Student> findAllByEmail(String email);

    @EntityGraph(attributePaths = "courses")
    Optional<Student> findFirstByEmail(String email);


    Optional<Student> findByEmail(String email);


}
