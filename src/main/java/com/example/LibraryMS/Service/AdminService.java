package com.example.LibraryMS.Service;

import com.example.LibraryMS.Dto.LoginDto;
import com.example.LibraryMS.Entity.Admin;
import com.example.LibraryMS.Entity.Student;
import com.example.LibraryMS.Exception.InvalidCredentialsException;
import com.example.LibraryMS.Repository.AdminRepository;
import com.example.LibraryMS.Repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StudentRepository studentRepository;

    // Admin Login
    public String login(LoginDto loginDto) {
        Admin admin = adminRepository.findByEmail(loginDto.getEmail());
        if (admin == null || !BCrypt.checkpw(loginDto.getPassword(), admin.getPassword()))
            throw new InvalidCredentialsException("Invalid email or password!");
        return "Admin logged in successfully!";
    }

    // Add student (Admin only)
    public String addStudent(Student student) {
        validateStudent(student);
        studentRepository.save(student);
        return "Student added successfully!";
    }

    // Update student (Admin only)
    public String updateStudent(Long id, Student student) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if (!existingStudent.isPresent()) {
            throw new EntityNotFoundException("Student with ID " + id + " not found!");
        }
        Student s = existingStudent.get();
        s.setId(student.getId());
        s.setName(student.getName());
        s.setEmail(student.getEmail());
        s.setDept(student.getDept());
        s.setBatch(student.getBatch());
        s.setInterest(student.getInterest());
        studentRepository.save(s);
        return "Student updated successfully!";
    }

    // Delete student (Admin only)
    public String deleteStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            throw new EntityNotFoundException("Student with ID " + id + " not found!");
        }
        studentRepository.deleteById(id);
        return "Student deleted successfully!";
    }

    // Get all students (Admin only)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Search student by ID
    public Student searchStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            throw new EntityNotFoundException("Student with ID " + id + " not found!");
        }
        return student.get();
    }

    // Validate Student Object
    private void validateStudent(Student student) {
        if (student.getName() == null || student.getEmail() == null || student.getId() == null) {
            throw new IllegalArgumentException("Student name, email and Id cannot be null!");
        }
    }
}
