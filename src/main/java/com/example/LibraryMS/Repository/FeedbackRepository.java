package com.example.LibraryMS.Repository;
import com.example.LibraryMS.Entity.Admin;
import com.example.LibraryMS.Entity.Feedback;
import com.example.LibraryMS.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByStudentAndAdmin(Student student, Admin admin);
    // Custom queries can be added here if needed
}
