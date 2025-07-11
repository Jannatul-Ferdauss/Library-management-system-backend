package com.example.LibraryMS.Service;

import com.example.LibraryMS.Dto.BorrowsReturnDto;
import com.example.LibraryMS.Entity.BorrowsReturn;
import com.example.LibraryMS.Entity.Book;
import com.example.LibraryMS.Entity.Student;
import com.example.LibraryMS.Repository.BorrowsReturnRepository;
import com.example.LibraryMS.Repository.BookRepository;
import com.example.LibraryMS.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BorrowsReturnService {

    @Autowired
    private BorrowsReturnRepository borrowsReturnRepository;

    @Autowired
    private ReportService reportService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private StudentRepository studentRepository;

    public BorrowsReturnDto createBorrowsReturn(Long studentId, Long bookId, Date issuedDate, Date returnDate) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        BorrowsReturn borrowsReturn = new BorrowsReturn();
        borrowsReturn.setStudent(student);
        borrowsReturn.setBook(book);
        borrowsReturn.setStudentId(studentId);
        borrowsReturn.setBookId(bookId);
        borrowsReturn.setIssuedDate(issuedDate);
        borrowsReturn.setReturnDate(returnDate);

        // Calculate initial due value
        int due = 0;
        if (returnDate != null && returnDate.before(new Date())) {
            long daysOverdue = (new Date().getTime() - returnDate.getTime()) / (1000 * 60 * 60 * 24);
            due = (int) daysOverdue;
        }
        borrowsReturn.setDue(due);

        BorrowsReturn saved = borrowsReturnRepository.save(borrowsReturn);
        // Update the report after creating a borrow record
        reportService.generateOrUpdateReport(studentId);
        return convertToDto(saved);
    }

    public BorrowsReturnDto updateBorrowsReturn(Long studentId, Long bookId, Date issuedDate, Date returnDate) {
        BorrowsReturn borrowsReturn = borrowsReturnRepository.findById(new BorrowsReturn.BorrowsReturnKey(studentId, bookId))
                .orElseThrow(() -> new IllegalArgumentException("Record not found"));

        borrowsReturn.setIssuedDate(issuedDate);
        borrowsReturn.setReturnDate(returnDate);

        // Recalculate due value
        int due = 0;
        if (returnDate != null && returnDate.before(new Date())) {
            long daysOverdue = (new Date().getTime() - returnDate.getTime()) / (1000 * 60 * 60 * 24);
            due = (int) daysOverdue;
        }
        borrowsReturn.setDue(due);

        // Update due value in Report
        //reportService.generateOrUpdateReport(studentId);

        BorrowsReturn updated = borrowsReturnRepository.save(borrowsReturn);
        reportService.generateOrUpdateReport(studentId);
        return convertToDto(updated);
    }

    public void deleteBorrowsReturn(Long studentId, Long bookId) {
        BorrowsReturn borrowsReturn = borrowsReturnRepository.findById(new BorrowsReturn.BorrowsReturnKey(studentId, bookId))
                .orElseThrow(() -> new IllegalArgumentException("Record not found"));

        borrowsReturnRepository.delete(borrowsReturn);
        // Update the report after deletion
        reportService.generateOrUpdateReport(studentId);
    }

    private BorrowsReturnDto convertToDto(BorrowsReturn borrowsReturn) {
        return new BorrowsReturnDto(
                borrowsReturn.getStudentId(),
                borrowsReturn.getBookId(),
                borrowsReturn.getIssuedDate(),
                borrowsReturn.getReturnDate(),
                borrowsReturn.getDue()
        );
    }

    public Map<String, Object> getBooksAndDetailsByStudentId(Long studentId) {
        List<BorrowsReturn> borrows = borrowsReturnRepository.findByStudentId(studentId);
        Long count = (long) borrows.size();

        List<Map<String, Object>> bookDetails = borrows.stream().map(borrow -> {
            Book book = bookRepository.findById(borrow.getBookId())
                    .orElseThrow(() -> new IllegalArgumentException("Book not found for ID: " + borrow.getBookId()));
            Map<String, Object> details = new HashMap<>();
            details.put("title", book.getTitle());
            details.put("author", book.getAuthor());
            details.put("issuedDate", borrow.getIssuedDate());
            details.put("returnDate", borrow.getReturnDate());
            details.put("due", borrow.getDue());
            return details;
        }).toList();

        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("books", bookDetails);

        return result;
    }

    // Scheduled to run at midnight every day
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateDues() {
        List<BorrowsReturn> overdueRecords = borrowsReturnRepository.findAll()
                .stream()
                .filter(borrow -> borrow.getReturnDate() != null && borrow.getReturnDate().before(new Date()))
                .toList();

        for (BorrowsReturn borrow : overdueRecords) {
            borrow.setDue(borrow.getDue() + 1);
            borrowsReturnRepository.save(borrow);
 }
}

    public List<BorrowsReturnDto> getAllBorrowsReturns() {
        // Fetch all borrows return records from the repository
        List<BorrowsReturn> borrowsReturnList = borrowsReturnRepository.findAll();

        // Convert each entity to DTO and return as a list
        return borrowsReturnList.stream()
                .map(borrowsReturn -> new BorrowsReturnDto(
                        borrowsReturn.getStudentId(),
                        borrowsReturn.getBookId(),
                        borrowsReturn.getIssuedDate(),
                        borrowsReturn.getReturnDate(),
                        borrowsReturn.getDue()))
                .collect(Collectors.toList());
    }

    public List<BorrowsReturnDto> getDueBooksByStudentId(Long studentId) {
        List<BorrowsReturn> dueBorrows = borrowsReturnRepository.findDueBooksByStudentId(studentId);

        return dueBorrows.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

}
