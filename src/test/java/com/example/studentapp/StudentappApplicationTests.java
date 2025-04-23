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
class StudentappApplicationTests {

	private WebDriver driver;

	@BeforeEach
	public void setUp() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
		driver = new ChromeDriver(); // 🔥 dikkat: 'WebDriver' değil sadece 'driver'
		driver.get("http://localhost:8080/students/add");
		Thread.sleep(1000);
	}

	@Test
	public void testAddStudentForm() throws InterruptedException {
		WebElement vornameField = driver.findElement(By.id("vorname"));
		WebElement nachnameField = driver.findElement(By.id("nachname"));
		WebElement emailField = driver.findElement(By.id("email"));
		WebElement geburtsdatumField = driver.findElement(By.id("geburtsdatum"));
		WebElement passwordField = driver.findElement(By.id("password"));


		Thread.sleep(1000);

		vornameField.sendKeys("Tom");
		nachnameField.sendKeys("Yılmazz");
		emailField.sendKeys("atom@example.com");
		geburtsdatumField.sendKeys("2000-01-01");
		passwordField.sendKeys("12345");

		WebElement saveButton = driver.findElement(By.cssSelector("button[type='submit']"));
		saveButton.click();

		Thread.sleep(1000);

		assertTrue(driver.getCurrentUrl().contains("/students"));
	}

	@AfterEach
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
