package com.example.LibraryMS.Service;

import com.example.LibraryMS.Dto.LoginDto;
import com.example.LibraryMS.Dto.SignupDto;
import com.example.LibraryMS.Dto.StudentPhoneDto;
import com.example.LibraryMS.Entity.Student;
import com.example.LibraryMS.Entity.StudentPhone;
import com.example.LibraryMS.Repository.StudentPhoneRepository;
import com.example.LibraryMS.Repository.StudentRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentPhoneRepository studentPhoneRepository;


    // Student Signup
    public String signup(SignupDto signupDto) {
        // Check if the student with the given email already exists
        if (studentRepository.existsByEmail(signupDto.getEmail())) {
            throw new IllegalArgumentException("Student with this email already exists!");
        }

        Student student = new Student();
        student.setId(signupDto.getId());
        student.setName(signupDto.getName());
        student.setEmail(signupDto.getEmail());
        student.setPassword(BCrypt.hashpw(signupDto.getPassword(), BCrypt.gensalt())); // Hash the password
        student.setDept(signupDto.getDept());
        student.setBatch(signupDto.getBatch());
        student.setAddress(signupDto.getAddress());  // Assuming you have an address field in Student
        student.setInterest(signupDto.getInterest()); // Assuming you have an interest field in Student

        studentRepository.save(student);


        // Add phone numbers from the DTO to the StudentPhone table
        if (signupDto.getPhones() != null) {
            for (StudentPhoneDto phoneDto : signupDto.getPhones()) {
                StudentPhone studentPhone = new StudentPhone();
                studentPhone.setStudentId(student.getId());
                studentPhone.setPhoneNumber(phoneDto.getPhoneNumber());
                studentPhone.setStudent(student);

                // Save each phone number associated with the student
                studentPhoneRepository.save(studentPhone);
            }
        }

        return "Student signed up successfully with phones!";
    }

    // Student Login
    public String login(LoginDto loginDto) {
        // Find the student by email
        Optional<Student> studentOpt = Optional.ofNullable(studentRepository.findByEmail(loginDto.getEmail()));

        // If the student is found, check if the password matches
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            if (BCrypt.checkpw(loginDto.getPassword(), student.getPassword())) {
                return "Student logged in successfully!";
            } else {
                throw new IllegalArgumentException("Invalid email or password!");
            }
        } else {
            throw new IllegalArgumentException("Student not found with the given email!");
        }
    }

    // Fetch Student by Email
    public Student getStudentByEmail(String email) {
        // Find the student by email
        Optional<Student> studentOpt = Optional.ofNullable(studentRepository.findByEmail(email));
        if (studentOpt.isPresent()) {
            return studentOpt.get();
        } else {
            throw new IllegalArgumentException("Student not found with the given email!");
        }
    }

}

