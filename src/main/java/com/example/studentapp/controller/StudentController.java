package com.example.studentapp.controller;

import com.example.studentapp.model.Course;
import com.example.studentapp.model.Student;
import com.example.studentapp.repository.CourseRepository;
import com.example.studentapp.repository.StudentRepository;
import com.example.studentapp.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    // Öğrenci listeleme ve form gösterimi tek sayfada
    @GetMapping
    public String showManagePage(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("student", new Student());
        model.addAttribute("courses", courseRepository.findAll());
        return "student-management";
    }

    // Öğrenci kaydet (yeni veya güncelle)
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("student") Student student,
                              @RequestParam(required = false, name = "courses") List<Long> courseIds) {

        if (courseIds != null && !courseIds.isEmpty()) {
            Set<Course> selectedCourses = new HashSet<>(courseRepository.findAllById(courseIds));
            student.setCourses(selectedCourses);
        } else {
            student.setCourses(new HashSet<>()); // boş set
        }

        studentRepository.save(student);
        return "redirect:/students";
    }

    // Güncelleme işlemi için sayfa yeniden çağrılır
    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige ID: " + id));
        model.addAttribute("student", student);
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("courses", courseRepository.findAll());
        return "student-management";
    }

    // Silme işlemi
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
        return "redirect:/students";
    }


    @PostMapping("/profil/courses")
    public String updateCourses(@RequestParam("courseIds") List<Long> courseIds, HttpSession session) {
        Student student = (Student) session.getAttribute("loggedInStudent");

        if (student == null) return "redirect:/login";

        Set<Course> selectedCourses = new HashSet<>(courseRepository.findAllById(courseIds));
        student.setCourses(selectedCourses);
        studentRepository.save(student);

        return "redirect:/profil";
    }

    @GetMapping("/profil")
    public String showProfile(HttpSession session, Model model) {
        // öğrenci bilgilerini ekle
        return "profil"; // templates/profil.html
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute Student student,
                                @RequestParam(required = false) String newPassword,
                                RedirectAttributes redirectAttributes) {

        if (newPassword != null && !newPassword.isEmpty()) {
            student.setPassword(passwordEncoder.encode(newPassword));
        } else {
            Student existing = studentService.findById(student.getId()).orElse(null);
            if (existing != null) {
                student.setPassword(existing.getPassword());
            }
        }

        studentService.save(student);

        redirectAttributes.addFlashAttribute("success", "Profil wurde erfolgreich aktualisiert!");

        return "redirect:/profile";
    }



}
