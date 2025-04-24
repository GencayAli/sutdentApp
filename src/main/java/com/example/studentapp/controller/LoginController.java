package com.example.studentapp.controller;

import com.example.studentapp.model.Course;
import com.example.studentapp.model.Student;
import com.example.studentapp.repository.CourseRepository;
import com.example.studentapp.repository.StudentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class LoginController {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;


    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model,
                        HttpSession session) {

        Optional<Student> optionalStudent = studentRepository.findFirstByEmail(email);

        if (optionalStudent.isPresent() && password.equals(optionalStudent.get().getPassword())) {
            session.setAttribute("loggedInStudent", optionalStudent.get());
            return "redirect:/profil";
        }

        model.addAttribute("loginError", "Falsche E-Mail oder Passwort");
        return "login";
    }


    @GetMapping("/profil")
    public String showStudentProfile(HttpSession session, Model model) {
        Student sessionStudent = (Student) session.getAttribute("loggedInStudent");

        if (sessionStudent == null) {
            return "redirect:/login";
        }

        // Veritabanından öğrenci ilişkili kurslarla birlikte yeniden alınır
        Student fullStudent = studentRepository.findById(sessionStudent.getId()).orElse(null);

        if (fullStudent == null) {
            return "redirect:/login";
        }

        model.addAttribute("student", fullStudent);
        model.addAttribute("allCourses", courseRepository.findAll());

        return "student-profile";
    }


    @PostMapping("/profil/courses")
    public String updateCourses(@RequestParam(name = "courseIds", required = false) List<Long> courseIds,
                                HttpSession session) {
        Student loggedInStudent = (Student) session.getAttribute("loggedInStudent");

        if (loggedInStudent == null) {
            return "redirect:/login";
        }

        Set<Course> selectedCourses = new HashSet<>();
        if (courseIds != null) {
            selectedCourses.addAll(courseRepository.findAllById(courseIds));
        }

        loggedInStudent.setCourses(selectedCourses);
        studentRepository.save(loggedInStudent);

        session.setAttribute("loggedInStudent", loggedInStudent); // güncellenmişi tekrar kaydet

        return "redirect:/profil"; // tekrar profili göster
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // oturumu sıfırla
        return "redirect:/login"; // login sayfasına yönlendir
    }


}
