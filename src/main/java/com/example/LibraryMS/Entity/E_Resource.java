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
@Table(name = "Ebook") // Matches the table name in the database
public class E_Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eresourceId;

    @Column(name = "Cover") // New column for the cover URL
    private String cover;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "PDF_URL", nullable = false)
    private String pdf;

    @Column(name = "Audio_Link")
    private String audioLink;

}