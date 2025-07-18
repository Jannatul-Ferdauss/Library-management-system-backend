package com.example.LibraryMS.Repository;


import com.example.LibraryMS.Entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Report findByStudentId(Long studentId);
}
