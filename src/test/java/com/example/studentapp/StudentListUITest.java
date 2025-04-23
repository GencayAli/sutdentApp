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
