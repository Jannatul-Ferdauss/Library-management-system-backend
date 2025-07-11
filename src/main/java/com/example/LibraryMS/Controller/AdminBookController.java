package com.example.LibraryMS.Controller;

import com.example.LibraryMS.Dto.BookDto;
import com.example.LibraryMS.Exception.ResourceNotFoundException;
import com.example.LibraryMS.Service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminBookController {
    private final BookService bookService;

    // Add Book
    @PostMapping("/books")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookDto savedBook = bookService.createBook(bookDto);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    // Get Book by ID
    @GetMapping("/books/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long bookId) {
        BookDto bookDto = bookService.getBookById(bookId);
        return ResponseEntity.ok(bookDto);
    }

    // Get All Books
    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    // Get Books by Category
    @GetMapping("/books/category/{category}")
    public ResponseEntity<List<BookDto>> getBooksByCategory(@PathVariable("category") String category) {
        List<BookDto> books = bookService.getBooksByCategory(category);
        return ResponseEntity.ok(books);
    }

    // Update Book
    @PutMapping("/books/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable("id") Long bookId,
                                              @RequestBody BookDto updatedBook) {
        BookDto bookDto = bookService.updateBook(bookId, updatedBook);
        return ResponseEntity.ok(bookDto);
    }

    // Delete Book
    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok("Book successfully deleted");
    }

    // Get Book by Title
    @GetMapping("/books/title/{title}")
    public ResponseEntity<BookDto> getBookByTitle(@PathVariable("title") String title) {
        BookDto bookDto = bookService.getBookByTitle(title);
        return ResponseEntity.ok(bookDto);
    }

    // Get Book by Author
    @GetMapping("/books/author/{author}")
    public ResponseEntity<BookDto> getBookByAuthor(@PathVariable("author") String author) {
        BookDto bookDto = bookService.getBookByAuthor(author);
        return ResponseEntity.ok(bookDto);
    }


}