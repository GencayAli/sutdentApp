package com.example.studentapp.controller;

import com.example.studentapp.model.Course;
import com.example.studentapp.model.Student;
import com.example.studentapp.repository.CourseRepository;
import com.example.studentapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    // Tüm öğrencileri listele
    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "student-list";
    }

    // Yeni öğrenci formu
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("allCourses", courseRepository.findAll()); // HTML'de th:each="kurs : ${allCourses}"
        return "student-form";
    }

    // Öğrenci kaydetme
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("student") Student student,
                              @RequestParam(required = false, name = "courses") List<Long> courseIds) {

        if (courseIds != null && !courseIds.isEmpty()) {
            Set<Course> selectedCourses = new HashSet<>(courseRepository.findAllById(courseIds));
            student.setCourses(selectedCourses);
        }

        studentRepository.save(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String studentUpdate(@PathVariable Long id, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ungültige ID: " + id));

        model.addAttribute("student", student); // Form için güncellenecek öğrenci
        model.addAttribute("students", studentRepository.findAll()); // Liste için
        model.addAttribute("courses", courseRepository.findAll()); // Kurslar

        return "student-manage"; // ✅ her şey tek sayfada
    }

    @GetMapping("/delete/{id}") public String loscheStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
        return "redirect:/students";
    }

    @GetMapping("/manage")
    public String manageStudents(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("student", new Student()); // form için boş nesne
        model.addAttribute("courses", courseRepository.findAll());
        return "student-manage"; // oluşturacağın birleşik sayfa
    }

}
