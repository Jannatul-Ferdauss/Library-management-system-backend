package com.example.LibraryMS.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class E_ResourceDto {
    private Long eresourceId;
    private String cover;
    private String title;
    private String pdf;
    private String audioLink;
}