package com.example.gdg_homework06.dto.reviewDto;

import com.example.gdg_homework06.domain.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponseDto {
    private Long id;
    private String name;
    private int score;
    private Long movieId;
    private String movieTitle;

    public static ReviewResponseDto of(Review review) {
        return ReviewResponseDto.builder()
                .id(review.getId())
                .name(review.getUser().getName())
                .score(review.getScore())
                .movieId(review.getMovie().getId())
                .movieTitle(review.getMovie().getTitle())
                .build();
    }

}
