package com.example.LibraryMS.Service;

import com.example.LibraryMS.Entity.Admin;
import com.example.LibraryMS.Entity.Feedback;
import com.example.LibraryMS.Entity.Student;
import com.example.LibraryMS.Repository.AdminRepository;
import com.example.LibraryMS.Repository.FeedbackRepository;
import com.example.LibraryMS.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdminRepository adminRepository;

    // Method to save feedback
    public Feedback saveFeedback(Long studentId, Long adminId, Feedback feedback) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new RuntimeException("Admin not found"));

        feedback.setStudent(student);
        feedback.setAdmin(admin);
        return feedbackRepository.save(feedback);
    }

    // Method to get all feedback for a specific admin
    public List<Feedback> getFeedbackByAdmin(Long adminId) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new RuntimeException("Admin not found"));
        return admin.getFeedbackList();
    }
}
