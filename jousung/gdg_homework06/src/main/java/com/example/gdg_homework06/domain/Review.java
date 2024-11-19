package com.example.gdg_homework06.domain;

import com.example.gdg_homework06.dto.reviewDto.ReviewRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private int score;

    public void updateField(ReviewRequestDto reviewRequestDto) {
        this.score = reviewRequestDto.getScore();
    }

    @Builder
    public Review(Movie movie, User user, int score) {
        this.movie = movie;
        this.user = user;
        this.score=score;
    }
}
