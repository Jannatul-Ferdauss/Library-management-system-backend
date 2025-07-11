package com.example.LibraryMS.Service;

import com.example.LibraryMS.Entity.BorrowsReturn;
import com.example.LibraryMS.Entity.Report;
import com.example.LibraryMS.Repository.BorrowsReturnRepository;
import com.example.LibraryMS.Repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private BorrowsReturnRepository borrowsReturnRepository;

    @Autowired
    private ReportRepository reportRepository;

    // Calculate total dues for a student based on borrow_return
    public Double calculateTotalDues(Long studentId) {
        List<BorrowsReturn> borrows = borrowsReturnRepository.findByStudentId(studentId);

        double totalDue = 0.0;

        for (BorrowsReturn borrow : borrows) {
            // Recalculate due for overdue books
            if (borrow.getReturnDate() != null && borrow.getReturnDate().before(new Date())) {
                long daysOverdue = (new Date().getTime() - borrow.getReturnDate().getTime()) / (1000 * 60 * 60 * 24);
                borrow.setDue((int) daysOverdue); // Update the due in the entity
                borrowsReturnRepository.save(borrow); // Save updated due to DB
            }
            totalDue += borrow.getDue(); // Aggregate due
        }

        return totalDue;
    }


    public Report generateOrUpdateReport(Long studentId) {
        List<BorrowsReturn> borrows = borrowsReturnRepository.findByStudentId(studentId);

        // Recalculate total dues dynamically
        Double totalDue = calculateTotalDues(studentId);

        String paymentStatus = totalDue > 0 ? "Unpaid" : "Paid";

        // Find existing report or create a new one
        Report report = reportRepository.findByStudentId(studentId);
        if (report == null) {
            report = new Report();
            report.setStudentId(studentId);
        }

        report.setTotalDue(totalDue);
        report.setAmount(totalDue);  // Set payment amount equal to dues
        report.setPaymentStatus(paymentStatus);

        return reportRepository.save(report);
    }


    // Get report for a student
    public Report getReport(Long studentId) {
        return reportRepository.findByStudentId(studentId);
    }

    // Update the report when payment is made (clear dues)
    public void clearDues(Long studentId) {
        Report report = reportRepository.findByStudentId(studentId);
        if (report != null) {
            report.setPaymentStatus("Paid");
            report.setTotalDue(0.0);  // Set dues to zero after payment
            reportRepository.save(report);
        }
    }
}
