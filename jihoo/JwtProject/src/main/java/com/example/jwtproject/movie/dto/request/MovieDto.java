package com.example.jwtproject.movie.dto.request;

public record MovieDto(
        String title,
        String genre,
        String releaseDate
) {
}
