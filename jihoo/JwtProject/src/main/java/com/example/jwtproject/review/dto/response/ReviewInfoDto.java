package com.example.jwtproject.review.dto.response;

import com.example.jwtproject.review.domain.Review;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record ReviewInfoDto(
        Long id,
        String username,
        String movieTitle,
        String comment,
        String rating,
        LocalDateTime createdDate
) {
    public static ReviewInfoDto from(Review review) {
        return ReviewInfoDto.builder()
                .id(review.getId())
                .username(review.getUser().getUsername())
                .movieTitle(review.getMovie().getTitle())
                .comment(review.getComment())
                .rating(review.getRating())
                .createdDate(review.getCreatedDate())
                .build();
    }
}
