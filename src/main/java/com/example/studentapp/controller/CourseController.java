package com.example.studentapp.controller;

import com.example.studentapp.model.Course;
import com.example.studentapp.model.Student;
import com.example.studentapp.repository.CourseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/kurse")
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    @GetMapping("/add")
    public String zeigeKursFormular(Model model){
        model.addAttribute("kurs", new Course());
        return "kurs-formular";
    }

    @PostMapping("/speichern")
    public String speichereKurs(@ModelAttribute("kurs") Course kurs, Model model) {
        List<Course> existingCourses = courseRepository.findByName(kurs.getName());

        if (!existingCourses.isEmpty()) {
            model.addAttribute("fehler", "Ein Kurs mit diesem Namen existiert bereits!");
            return "kurs-formular";
        }

        courseRepository.save(kurs);
        return "redirect:/kurse";
    }


    @GetMapping
    public String zeigeAlleKurse(Model model) {
        model.addAttribute("kursListe", courseRepository.findAllWithStudents());
        return "kurs-liste";
    }

    @GetMapping("/{id}/students")
    public String zeigeStudentenFürKurs(@PathVariable("id") Long id, Model model) {
        Course kurs = courseRepository.findById(id).orElse(null);
        if (kurs != null) {
            model.addAttribute("kurs", kurs);
            model.addAttribute("studenten", kurs.getStudents());
        }
        return "kurs-studenten";
    }

    @GetMapping("/edit/{id}")
    public  String courseUpdate(@PathVariable Long id, Model model){
        Course course = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ungültige ID: " + id) );
        model.addAttribute("kurs", course);

        return "kurs-formular";
    }
    @GetMapping("/delete/{id}")
    public String löscheKurse(@PathVariable Long id) {
        courseRepository.deleteById(id);
        return "redirect:/kurse";
    }
}
