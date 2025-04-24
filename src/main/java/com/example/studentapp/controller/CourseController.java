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
import java.util.Set;

@Controller
@RequestMapping("/kurse")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    //private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public String showCourseManagePage(Model model) {
        model.addAttribute("kursListe", courseRepository.findAll());
        model.addAttribute("kurs", new Course());
        return "kurs-management";
    }

    @GetMapping("/edit/{id}")
    public String editCourse(@PathVariable Long id, Model model) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige ID: " + id));
        model.addAttribute("kurs", course);
        model.addAttribute("kursListe", courseRepository.findAll());
        return "kurs-management";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        Course kurs = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige ID: " + id));

        // Öğrencilerden kursu kaldır
        for (Student s : kurs.getStudents()) {
            s.getCourses().remove(kurs);
        }

        courseRepository.delete(kurs);
        return "redirect:/kurse";
    }


    @PostMapping("/speichern")
    public String saveCourse(@ModelAttribute("kurs") Course course, Model model) {
        // Aynı isimde kurs varsa tekrar ekleme (isteğe bağlı)
        if (courseRepository.existsByName(course.getName())) {
            model.addAttribute("fehler", "Kursname bereits vorhanden!");
            model.addAttribute("kursListe", courseRepository.findAll());
            return "kurs-management";
        }

        courseRepository.save(course);
        return "redirect:/kurse";
    }

    @GetMapping("/{id}/students")
    public String showCourseWithStudents(@PathVariable Long id, Model model) {
        Course kurs = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ungültige ID: " + id));
        model.addAttribute("kurs", kurs);
        model.addAttribute("studenten", kurs.getStudents());

        model.addAttribute("kursListe", courseRepository.findAll());
        model.addAttribute("kurs", kurs); // tekrar form için
        return "kurs-management";
    }

    @PostMapping("/profil/courses")
    public String updateCourses(@RequestParam("courseIds") List<Long> courseIds, HttpSession session) {
        Student student = (Student) session.getAttribute("loggedInStudent");

        if (student == null) return "redirect:/login";

        Set<Course> selectedCourses = new HashSet<>(courseRepository.findAllById(courseIds));
        student.setCourses(selectedCourses);
        studentRepository.save(student);

        return "redirect:/student-profil";
    }

    @GetMapping("/profil")
    public String showProfile(HttpSession session, Model model) {
        // öğrenci bilgilerini ekle
        return "student-profile"; // templates/profil.html
    }


}
