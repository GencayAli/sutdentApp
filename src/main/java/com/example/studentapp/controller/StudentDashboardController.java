package com.example.studentapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentDashboardController {

    @GetMapping("/dashboard")
    public String studentDashboard() {
        return "student-dashboard"; // resources/templates/student-dashboard.html
    }
}
