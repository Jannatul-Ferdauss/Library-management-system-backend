package com.example.LibraryMS.Controller;

import com.example.LibraryMS.Dto.LoginDto;
import com.example.LibraryMS.Entity.Admin;
import com.example.LibraryMS.Entity.Student;
import com.example.LibraryMS.Service.AdminService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/students")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Admin Login
    @PostMapping("/login")
    public ResponseEntity<String> adminLogin(@RequestBody LoginDto loginDto) {
        try {
            String response = adminService.login(loginDto);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Fetch all students
    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        try {
            List<Student> students = adminService.getAllStudents();
            return ResponseEntity.ok(students);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error fetching students: " + ex.getMessage());
        }
    }

    // Fetch a student by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        try {
            Student student = adminService.searchStudentById(id);
            return ResponseEntity.ok(student);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(404).body("Student not found: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error fetching student: " + ex.getMessage());
        }
    }

    // Add a new student
    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        try {
            String addedStudent = adminService.addStudent(student);
            return ResponseEntity.ok(addedStudent);
        } catch (Exception ex) {
            return ResponseEntity.status(400).body("Error adding student: " + ex.getMessage());
        }
    }

    // Update an existing student
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        try {
            String updatedStudent = adminService.updateStudent(id, student);
            return ResponseEntity.ok(updatedStudent);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(404).body("Student not found: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error updating student: " + ex.getMessage());
        }
    }

    // Delete a student
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            adminService.deleteStudent(id);
            return ResponseEntity.ok("Student deleted successfully");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(404).body("Student not found: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error deleting student: " + ex.getMessage());
        }
    }
}
