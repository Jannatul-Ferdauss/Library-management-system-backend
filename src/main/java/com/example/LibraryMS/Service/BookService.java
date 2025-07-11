package com.example.LibraryMS.Service;

import com.example.LibraryMS.Dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto createBook(BookDto bookDto);

    BookDto getBookById(Long id);

    List<BookDto> getAllBooks();

    BookDto updateBook(Long id, BookDto updateBook);

    void deleteBook(Long id);

    BookDto getBookByAuthor(String author);

    BookDto getBookByTitle(String title);

    List<BookDto> getBooksByCategory(String category);

}