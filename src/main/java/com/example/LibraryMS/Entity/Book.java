package com.example.LibraryMS.Entity;

import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Book")  // Matches the table name in the database
public class Book {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Cover") // Field to store cover URL
    private String cover;

    @Setter
    @Column(name = "Title")
    private String title;

    @Setter
    @Column(name = "Author")
    private String author;

    @Column(name = "Category")
    private String category;

    @Column(name = "Available")
    private Long available;

}
