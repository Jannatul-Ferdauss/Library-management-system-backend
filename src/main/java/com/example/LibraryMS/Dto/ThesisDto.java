package com.example.LibraryMS.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThesisDto {
    private Long thesisId;
    private String title;
    private String author;
    private int year;
    private String topic;
    private String fileUrl;
}
