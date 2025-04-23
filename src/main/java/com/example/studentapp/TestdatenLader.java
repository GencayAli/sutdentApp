package com.example.studentapp;

import com.example.studentapp.model.Course;
import com.example.studentapp.model.Student;
import com.example.studentapp.repository.CourseRepository;
import com.example.studentapp.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Set;

@Configuration
public class TestdatenLader {
    @Bean
    public CommandLineRunner initTestDaten(StudentRepository studentRepository, CourseRepository courseRepository) {
        return args -> {
            if (courseRepository.count() == 0) {
                Course kurs1 = new Course();
                kurs1.setName("Mathematik");

            Course kurs2 = new Course();
            kurs2.setName("Informatik");

            // Kurse in die Datenbank speichern
            courseRepository.save(kurs1);
            courseRepository.save(kurs2);

            // Beispielstudent erstellen
            Student student = new Student("Lisa", "Meier", "lisa@example.com", LocalDate.of(2001, 3, 15));
            student.setPassword("1234");

            // Kurse dem Studenten zuweisen
            student.setCourses(Set.of(kurs1, kurs2));

            // Student in die Datenbank speichern
            studentRepository.save(student);
        }
    };
}
}
