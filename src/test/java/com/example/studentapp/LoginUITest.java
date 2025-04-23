package com.example.studentapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LoginUITest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/login");
        Thread.sleep(1000);
    }

    @Test
    public void testLoginWithCorrectCredentials() throws InterruptedException {
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));

        // TestdatenLader sınıfında "admin" maili ve "12345" şifresiyle bir kullanıcı oluşturmuştuk
        emailField.sendKeys("admin@gmail.com");
        passwordField.sendKeys("12345");

        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();

        Thread.sleep(1000);

        // Login başarılıysa "/students" sayfasına yönlendirilmiş olmalı
        assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/students"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
       // System.out.println("Şu anki URL: " + driver.getCurrentUrl());

    }

}
