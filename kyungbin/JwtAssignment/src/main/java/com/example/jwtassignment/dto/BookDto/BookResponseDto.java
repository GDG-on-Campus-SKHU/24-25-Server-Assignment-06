package com.example.jwtassignment.dto.BookDto;

import com.example.jwtassignment.domain.book.Book;
import com.example.jwtassignment.dto.UserDto.UserInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private String review;

    // 응답을 위해 Entity값을 Dto로 바꾸어 준다.
    public BookResponseDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.review = book.getReview();
    }

    public static BookResponseDto from(Book book) {
        return BookResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .review(book.getReview())
                .build();
    }

}
