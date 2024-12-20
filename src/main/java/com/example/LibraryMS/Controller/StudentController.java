package com.example.LibraryMS.Controller;

import com.example.LibraryMS.Dto.LoginDto;
import com.example.LibraryMS.Dto.SignupDto;
import com.example.LibraryMS.Entity.Student;
import com.example.LibraryMS.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Student Signup
    @PostMapping("/signup")
    public ResponseEntity<String> studentSignup(@RequestBody SignupDto signupDto) {
        try {
            String response = studentService.signup(signupDto);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Student Login
    @PostMapping("/login")
    public ResponseEntity<String> studentLogin(@RequestBody LoginDto loginDto) {
        try {
            String response = studentService.login(loginDto);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @GetMapping("/profile")
    public ResponseEntity<Student> getStudentProfile(@RequestParam String email) {
        try {
            // Fetch the student using the email
            Student student = studentService.getStudentByEmail(email);
            return ResponseEntity.ok(student);
        } catch (IllegalArgumentException e) {
            // Handle case when student is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}

