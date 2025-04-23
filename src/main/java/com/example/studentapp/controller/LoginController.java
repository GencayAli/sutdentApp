package com.example.studentapp.controller;

import com.example.studentapp.model.Student;
import com.example.studentapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model) {

        Student student = studentRepository.findByEmail(email);

        if (student != null && password.equals(student.getPassword())) {
            // Giriş başarılı
            model.addAttribute("student", student);
            return "redirect:/students"; // ya da öğrencinin profil sayfası vs.
        } else {
            // Hatalı giriş
            model.addAttribute("loginError", "Falsche E-Mail oder Passwort");
            return "login";
        }
    }
}
