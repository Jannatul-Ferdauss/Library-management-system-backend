package com.example.LibraryMS.Repository;

import com.example.LibraryMS.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE LOWER(b.author) = LOWER(:author)")
    Optional<Book> findByAuthor(@Param("author") String author);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) = LOWER(:title)")
    Optional<Book> findByTitle(@Param("title") String title);

    List<Book> findByCategory(String category);

}