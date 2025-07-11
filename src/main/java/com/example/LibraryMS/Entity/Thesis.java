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
@Table(name = "Thesis_Library") // Database table name
public class Thesis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long thesisId;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Author", nullable = false)
    private String author;

    @Column(name = "Year", nullable = false)
    private int year;

    @Column(name = "Topic", nullable = false)
    private String topic;

    @Column(name = "File_URL", nullable = false)
    private String fileUrl;
}
