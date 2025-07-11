package com.example.LibraryMS.Service;

import com.example.LibraryMS.Dto.BookDto;
import com.example.LibraryMS.Entity.Book;
import com.example.LibraryMS.Exception.ResourceNotFoundException;
import com.example.LibraryMS.Repository.BookRepository;
import com.example.LibraryMS.Service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    @Override
    public BookDto createBook(BookDto bookDto) {
        // Manually mapping BookDto to Book
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setCategory(bookDto.getCategory());
        book.setAvailable(bookDto.getAvailable());
        book.setCover(bookDto.getCover());

        Book savedBook = bookRepository.save(book);

        // Manually mapping Book to BookDto
        BookDto savedBookDto = new BookDto();
        savedBookDto.setId(savedBook.getId());
        savedBookDto.setTitle(savedBook.getTitle());
        savedBookDto.setAuthor(savedBook.getAuthor());
        savedBookDto.setCategory(savedBook.getCategory());
        savedBookDto.setAvailable(savedBook.getAvailable());
        savedBookDto.setCover(savedBook.getCover());

        return savedBookDto;
    }

    @Override
    public BookDto getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book doesn't exist with given id: " + bookId));

        // Manually mapping Book to BookDto
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setCategory(book.getCategory());
        bookDto.setAvailable(book.getAvailable());
        bookDto.setCover(book.getCover());

        return bookDto;
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        // Manually mapping List<Book> to List<BookDto>
        return books.stream().map(book -> {
            BookDto bookDto = new BookDto();
            bookDto.setId(book.getId());
            bookDto.setTitle(book.getTitle());
            bookDto.setAuthor(book.getAuthor());
            bookDto.setCategory(book.getCategory());
            bookDto.setAvailable(book.getAvailable());
            bookDto.setCover(book.getCover());
            return bookDto;
        }).collect(Collectors.toList());
    }

    @Override
    public BookDto updateBook(Long bookId, BookDto updateBook) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book doesn't exist with given Id: " + bookId));

        // Updating fields manually
        book.setCover(updateBook.getCover());
        book.setAuthor(updateBook.getAuthor());
        book.setTitle(updateBook.getTitle());
        book.setCategory(updateBook.getCategory());
        book.setAvailable(updateBook.getAvailable());

        Book updatedBookObj = bookRepository.save(book);

        // Manually mapping Book to BookDto
        BookDto updatedBookDto = new BookDto();
        updatedBookDto.setId(updatedBookObj.getId());
        updatedBookDto.setTitle(updatedBookObj.getTitle());
        updatedBookDto.setAuthor(updatedBookObj.getAuthor());
        updatedBookDto.setCategory(updatedBookObj.getCategory());
        updatedBookDto.setAvailable(updatedBookObj.getAvailable());
        updatedBookDto.setCover(updatedBookObj.getCover());

        return updatedBookDto;
    }

    @Override
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book doesn't exist with given Id: " + bookId));

        bookRepository.deleteById(bookId);
    }

    @Override
    public BookDto getBookByAuthor(String author) {
        Book book = bookRepository.findByAuthor(author)
                .orElseThrow(() -> new RuntimeException("Book with author " + author + " not found"));

        // Manually mapping Book to BookDto
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setCategory(book.getCategory());
        bookDto.setAvailable(book.getAvailable());
        bookDto.setCover(book.getCover());

        return bookDto;
    }

    @Override
    public BookDto getBookByTitle(String title) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Book with title \"" + title + "\" not found"));

        // Manually mapping Book to BookDto
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setCategory(book.getCategory());
        bookDto.setAvailable(book.getAvailable());
        bookDto.setCover(book.getCover());

        return bookDto;
    }

    @Override
    public List<BookDto> getBooksByCategory(String category) {
        List<Book> books = bookRepository.findByCategory(category);

        // Manually mapping List<Book> to List<BookDto>
        return books.stream().map(book -> {
            BookDto bookDto = new BookDto();
            bookDto.setId(book.getId());
            bookDto.setTitle(book.getTitle());
            bookDto.setAuthor(book.getAuthor());
            bookDto.setCategory(book.getCategory());
            bookDto.setAvailable(book.getAvailable());
            bookDto.setCover(book.getCover());
            return bookDto;
        }).collect(Collectors.toList());
    }
}