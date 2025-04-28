package com.example.studentapp;

import com.example.studentapp.model.Course;
import com.example.studentapp.model.Student;
import com.example.studentapp.repository.CourseRepository;
import com.example.studentapp.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Set;

@Configuration
public class TestdatenLader {

    @Bean
    public CommandLineRunner initTestDaten(StudentRepository studentRepository, CourseRepository courseRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (courseRepository.count() == 0) {
                Course kurs1 = new Course();
                kurs1.setName("Mathematik");

                Course kurs2 = new Course();
                kurs2.setName("Informatik");

                courseRepository.save(kurs1);
                courseRepository.save(kurs2);
            }

            if (studentRepository.count() == 0) {
                // Student
                Student student = new Student("Lis", "Meierr", "lisa@examplee.com", LocalDate.of(2001, 3, 15));
                student.setPassword(passwordEncoder.encode("1234"));
                student.setRolle("STUDENT");
                student.setCourses(Set.of(courseRepository.findAll().get(0), courseRepository.findAll().get(1)));
                studentRepository.save(student);

                // Admin
                Student admin = new Student("Admin", "User", "admin@example.com", LocalDate.of(1990, 1, 1));
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRolle("ADMIN");
                studentRepository.save(admin);
            }
        };
    }
}
