package com.example.LibraryMS.Controller;

import com.example.LibraryMS.Dto.BookDto;
import com.example.LibraryMS.Dto.E_ResourceDto;
import com.example.LibraryMS.Exception.ResourceNotFoundException;
import com.example.LibraryMS.Service.BookService;
import com.example.LibraryMS.Service.E_ResourceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/student")
public class BookController {

    private final BookService bookService;
    private final E_ResourceService e_ResourceService;

    // Endpoint to get a book by author
    @GetMapping("/books/author/{author}")
    public ResponseEntity<BookDto> getBookByAuthor(@PathVariable("author") String author) {
        try {
            BookDto bookDto = bookService.getBookByAuthor(author);
            return ResponseEntity.ok(bookDto);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Handle exception gracefully
        }
    }

    // Endpoint to get a book by title
    @GetMapping("/books/title/{title}")
    public ResponseEntity<BookDto> getBookByTitle(@PathVariable("title") String title) {
        try {
            BookDto bookDto = bookService.getBookByTitle(title);
            return ResponseEntity.ok(bookDto);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Handle exception gracefully
        }
    }

    // Endpoint to get an ebook by title
    @GetMapping("/ebooks/title/{title}")
    public ResponseEntity<E_ResourceDto> getE_ResourceByTitle(@PathVariable("title") String title) {
        try {
            E_ResourceDto e_resourceDto = e_ResourceService.getE_ResourceByTitle(title);
            return ResponseEntity.ok(e_resourceDto);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Handle exception gracefully
        }
    }
}
