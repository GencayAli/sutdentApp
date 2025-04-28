package com.example.studentapp;

import com.example.studentapp.model.Student;
import com.example.studentapp.repository.StudentRepository;
import com.example.studentapp.service.StudentService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class StudentappApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(StudentappApplication.class, args);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode("1234");
		System.out.println(encodedPassword);
	}

	@PostConstruct
	public void init() {
		StudentService studentService = null;
		if (studentService.findByEmail("test@mail.com") == null) {
			Student student = new Student();
			student.setEmail("test@mail.com");
			student.setVorname("Max");
			student.setNachname("Mustermann");
			student.setPassword(passwordEncoder.encode("1234")); // Şifreyi encoder ile encode etmeyi unutma
			student.setRolle("STUDENT");
			studentService.save(student);
		}
	}


}
