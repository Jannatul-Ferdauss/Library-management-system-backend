package com.example.LibraryMS.Controller;

import com.example.LibraryMS.Dto.BorrowsReturnDto;
import com.example.LibraryMS.Service.BorrowsReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/borrows-return")
public class BorrowsReturnController {

    @Autowired
    private BorrowsReturnService borrowsReturnService;

    @PostMapping("/create")
    public ResponseEntity<BorrowsReturnDto> createBorrowsReturn(@RequestBody BorrowsReturnDto borrowsReturnDto) {
        BorrowsReturnDto result = borrowsReturnService.createBorrowsReturn(
                borrowsReturnDto.getStudentId(),
                borrowsReturnDto.getBookId(),
                borrowsReturnDto.getIssuedDate(),
                borrowsReturnDto.getReturnDate()
        );
        return ResponseEntity.ok(result);
    }

    @GetMapping("/details/{studentId}")
    public ResponseEntity<Map<String, Object>> getBooksAndDetailsByStudent(@PathVariable Long studentId) {
        Map<String, Object> result = borrowsReturnService.getBooksAndDetailsByStudentId(studentId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{studentId}/{bookId}")
    public ResponseEntity<BorrowsReturnDto> updateBorrowsReturn(
            @PathVariable Long studentId,
            @PathVariable Long bookId,
            @RequestBody BorrowsReturnDto borrowsReturnDto) {

        BorrowsReturnDto result = borrowsReturnService.updateBorrowsReturn(
                studentId,
                bookId,
                borrowsReturnDto.getIssuedDate(),
                borrowsReturnDto.getReturnDate()
        );
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BorrowsReturnDto>> getAllBorrowsReturns() {
        // Fetch all records from the service
        List<BorrowsReturnDto> result = borrowsReturnService.getAllBorrowsReturns();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/due-books/{studentId}")
    public ResponseEntity<List<BorrowsReturnDto>> getDueBooksByStudent(@PathVariable Long studentId) {
        List<BorrowsReturnDto> dueBooks = borrowsReturnService.getDueBooksByStudentId(studentId);
        return ResponseEntity.ok(dueBooks);
    }

    @DeleteMapping("/delete/{studentId}/{bookId}")
    public ResponseEntity<String> deleteBorrowsReturn(
            @PathVariable Long studentId,
            @PathVariable Long bookId) {

        borrowsReturnService.deleteBorrowsReturn(studentId, bookId);
        return ResponseEntity.ok("BorrowsReturn successfully deleted");
    }
}