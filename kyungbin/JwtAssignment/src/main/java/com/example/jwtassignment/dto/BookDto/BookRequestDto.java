package com.example.jwtassignment.dto.BookDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookRequestDto {
    //private Long id;
    private String title;
    private String author;
    private String review;


}
