package com.example.LibraryMS.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowsReturnDto {
    private Long studentId;
    private Long bookId;
    private Date issuedDate;
    private Date returnDate;
    private int due;
}