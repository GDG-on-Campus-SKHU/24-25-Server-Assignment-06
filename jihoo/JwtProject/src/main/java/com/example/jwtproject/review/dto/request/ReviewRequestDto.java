package com.example.jwtproject.review.dto.request;

public record ReviewRequestDto(
        Long movieId,
        String comment,
        String rating
) {
}
