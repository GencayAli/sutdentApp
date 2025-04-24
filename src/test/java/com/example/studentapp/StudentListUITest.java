package com.example.studentapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class StudentListUITest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void testStudentListContainsLisa() throws InterruptedException {
        driver.get("http://localhost:8080/students");
        Thread.sleep(1000);

        String pageSource = driver.getPageSource();

        // Öğrencinin ismi sayfada geçiyor mu?
        assertTrue(pageSource.contains("Lisa"));
        assertTrue(pageSource.contains("Meier"));
        assertTrue(pageSource.contains("lisa@example.com"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}


/*
* Öğrenciler için:
1. student-list.html'e "Bearbeiten" ve "Löschen" butonları ekle:
htmlKopierenBearbeiten<td>
    <a th:href="@{'/students/edit/' + ${student.id}}" class="btn btn-warning btn-sm">Bearbeiten</a>
    <a th:href="@{'/students/delete/' + ${student.id}}" class="btn btn-danger btn-sm"onclick="return confirm('Möchten Sie diesen Studenten wirklich löschen?')">Löschen</a></td>
2. StudentController.java içine aşağıdaki metotları ekle:
javaKopierenBearbeiten@GetMapping("/edit/{id}") public String zeigeEditForm(@PathVariable Long id, Model model) {
    Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ungültige ID: " + id));
    model.addAttribute("student", student);
    model.addAttribute("allCourses", courseRepository.findAll());
    return "student-form";
}
@GetMapping("/delete/{id}") public String löscheStudent(@PathVariable Long id) {
    studentRepository.deleteById(id);
    return "redirect:/students";
}
📘 Kurslar için:
1. kurs-liste.html'e "Bearbeiten" ve "Löschen" butonları ekle:
htmlKopierenBearbeiten<td>
    <a th:href="@{'/kurse/edit/' + ${kurs.id}}" class="btn btn-warning btn-sm">Bearbeiten</a>
    <a th:href="@{'/kurse/delete/' + ${kurs.id}}" class="btn btn-danger btn-sm"onclick="return confirm('Möchten Sie diesen Kurs wirklich löschen?')">Löschen</a></td>
2. CourseController.java içine ekle:
javaKopierenBearbeiten@GetMapping("/edit/{id}") public String zeigeKursBearbeitenForm(@PathVariable Long id, Model model) {
    Course kurs = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ungültige Kurs-ID: " + id));
    model.addAttribute("kurs", kurs);
    return "kurs-formular";
}
@GetMapping("/delete/{id}") public String löscheKurs(@PathVariable Long id) {
    courseRepository.deleteById(id);
    return "redirect:/kurse";
}
* */