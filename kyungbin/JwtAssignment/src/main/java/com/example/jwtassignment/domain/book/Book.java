package com.example.jwtassignment.domain.book;

import com.example.jwtassignment.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    //@ManyToOne 관계에서는 외래 키를 통해 다른 엔티티와 연결하므로, 정확하게 하기 위해 @JoinColumn으로 외래 키를 정의해야한대

    private User user;

    @Builder
    public Book(String title, String author, String review, User user) {
        this.title = title;
        this.author = author;
        this.review = review;
        this.user = user;
    }

    public void updateBook(String title, String author, String review) {
        this.title = title;
        this.author = author;
        this.review = review;
    }

    public void updateReview(String review) {
        this.review = review;
    }
}
