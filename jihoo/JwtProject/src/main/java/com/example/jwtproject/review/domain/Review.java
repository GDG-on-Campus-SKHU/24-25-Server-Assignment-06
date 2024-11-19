package com.example.jwtproject.review.domain;

import com.example.jwtproject.review.dto.request.ReviewRequestDto;
import com.example.jwtproject.movie.domain.Movie;
import com.example.jwtproject.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private String comment;

    private String rating;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @Builder
    public Review(User user, Movie movie, String comment, String rating) {
        this.user = user;
        this.movie = movie;
        this.comment = comment;
        this.rating = rating;
    }

    public void update(ReviewRequestDto reviewRequestDto) {
        this.comment = reviewRequestDto.comment();
        this.rating = reviewRequestDto.rating();
    }
}
