package com.example.LibraryMS.Service;

import com.example.LibraryMS.Entity.BorrowsReturn;
import com.example.LibraryMS.Entity.Payment;
import com.example.LibraryMS.Entity.Report;
import com.example.LibraryMS.Entity.Student;
import com.example.LibraryMS.Dto.PaymentDto;
import com.example.LibraryMS.Repository.BorrowsReturnRepository;
import com.example.LibraryMS.Repository.PaymentRepository;
import com.example.LibraryMS.Repository.ReportRepository;
import com.example.LibraryMS.Repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private BorrowsReturnRepository borrowsReturnRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private StudentRepository studentRepository;

    public PaymentService(PaymentRepository paymentRepository, StudentRepository studentRepository,
                          ReportRepository reportRepository) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
        this.reportRepository = reportRepository;
    }

    @Transactional
    public PaymentDto createPayment(PaymentDto paymentDTO) {
        // Fetch the student by studentId
        Student student = studentRepository.findById(paymentDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + paymentDTO.getStudentId()));

        // Fetch the associated report to get the total due
        Report report = reportRepository.findByStudentId(paymentDTO.getStudentId());
        if (report == null) {
            throw new RuntimeException("Report not found for student with ID: " + paymentDTO.getStudentId());
        }

        // Get the total due from the report
        double totalDue = report.getTotalDue();

        // Set the payment amount to totalDue
        paymentDTO.setAmount(totalDue);

        // Ensure the payment amount does not exceed the total dues
        if (paymentDTO.getAmount() > totalDue) {
            throw new IllegalArgumentException("Payment amount cannot exceed the total due.");
        }

        // Update the total due after payment
        double remainingDue = totalDue - paymentDTO.getAmount();
        report.setTotalDue(remainingDue);

        // Update the payment status: "Paid" if the total due is cleared, "Unpaid" otherwise
        String paymentStatus = remainingDue == 0 ? "Paid" : "Unpaid";
        report.setPaymentStatus(paymentStatus);

        // Save the updated report
        System.out.println("Before save - Total Due: " + report.getTotalDue() + ", Status: " + report.getPaymentStatus());
        reportRepository.save(report);
        reportRepository.flush();

        // Create a new Payment entity and set its properties, including transactionId
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setMethod(paymentDTO.getMethod());
        payment.setPaid(remainingDue == 0);
        payment.setStudent(student);
        payment.setTransactionId(paymentDTO.getTransactionId()); // Set the transaction ID

        // Save the payment
        Payment savedPayment = paymentRepository.save(payment);

        // Adjust the dues in BorrowsReturn after payment is made
        List<BorrowsReturn> borrowsReturnList = borrowsReturnRepository.findByStudentId(paymentDTO.getStudentId());
        for (BorrowsReturn borrowReturn : borrowsReturnList) {
            double newDue = borrowReturn.getDue() - paymentDTO.getAmount(); // Adjust due
            borrowReturn.setDue((int) newDue);

            // If the due is cleared, update the payment status accordingly
            if (newDue <= 0) {
                borrowReturn.setDue(0); // Ensure due is not negative
            }
            borrowsReturnRepository.save(borrowReturn);
        }

        // Map the saved entity to DTO and return
        return new PaymentDto(savedPayment.getId(), savedPayment.getAmount(), savedPayment.getMethod(),
                savedPayment.getStudent().getId(), savedPayment.isPaid(), report.getTotalDue(), savedPayment.getTransactionId());
    }

    public List<PaymentDto> getPaymentsById(Long studentId) {
        List<Payment> payments = paymentRepository.findByStudentId(studentId);

        return payments.stream()
                .map(payment -> {
                    Report report = reportRepository.findByStudentId(studentId);
                    double totalDue = (report != null) ? report.getTotalDue() : 0;

                    return new PaymentDto(
                            payment.getId(),
                            payment.getAmount(),
                            payment.getMethod(),
                            payment.getStudent().getId(),
                            payment.isPaid(),
                            totalDue,
                            payment.getTransactionId() // Include transactionId in the DTO
                    );
                })
                .collect(Collectors.toList());
    }

    public List<PaymentDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();

        return payments.stream()
                .map(payment -> {
                    Report report = reportRepository.findByStudentId(payment.getStudent().getId());
                    double totalDue = (report != null) ? report.getTotalDue() : 0;

                    return new PaymentDto(
                            payment.getId(),
                            payment.getAmount(),
                            payment.getMethod(),
                            payment.getStudent().getId(),
                            payment.isPaid(),
                            totalDue,
                            payment.getTransactionId() // Include transactionId in the DTO
                    );
                })
                .collect(Collectors.toList());
    }

    public void deletePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + paymentId));

        paymentRepository.delete(payment);
        System.out.println("Deleted payment with ID: " + paymentId);
    }
}