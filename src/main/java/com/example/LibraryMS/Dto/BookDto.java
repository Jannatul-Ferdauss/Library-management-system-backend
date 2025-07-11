package com.example.LibraryMS.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;

    private String cover;

    private String title;

    private String author;

    private String category;

    private Long available;
}
