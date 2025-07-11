package com.example.LibraryMS.Controller;

import com.example.LibraryMS.Entity.Report;
import com.example.LibraryMS.Repository.ReportRepository;
import com.example.LibraryMS.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportRepository reportRepository;

    // Endpoint to generate or update the report for a student
    @GetMapping("/generate/{studentId}")
    public ResponseEntity<Report> generateReport(@PathVariable Long studentId) {
        Report report = reportService.generateOrUpdateReport(studentId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/allreport")
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }


    // Endpoint to fetch the report for a student
    @GetMapping("/{studentId}")
    public ResponseEntity<Report> getReport(@PathVariable Long studentId) {
        Report report = reportService.getReport(studentId);
        if (report == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(report);
    }

    // In ReportController
    @PutMapping("/clearDues/{studentId}")
    public ResponseEntity<Report> clearDues(@PathVariable Long studentId) {
        reportService.clearDues(studentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }


}