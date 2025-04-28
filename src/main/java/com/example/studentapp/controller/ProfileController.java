package com.example.studentapp.controller;


import com.example.studentapp.model.Student;
import com.example.studentapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.security.Principal;


@Controller
public class ProfileController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        String email = principal.getName();
        Student student = studentService.findByEmail(email);

        if (student == null) {
            // Kullanıcı bulunamadıysa, hata sayfası göster veya logout yap
            return "redirect:/login?error"; // veya hata sayfası
        }

        model.addAttribute("student", student);
        return "profile";
    }

}
