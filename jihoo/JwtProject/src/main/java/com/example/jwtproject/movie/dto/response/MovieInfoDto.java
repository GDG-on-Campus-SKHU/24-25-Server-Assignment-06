package com.example.jwtproject.movie.dto.response;

import com.example.jwtproject.movie.domain.Movie;
import lombok.Builder;

@Builder
public record MovieInfoDto(
        Long id,
        String title,
        String genre,
        String releaseDate
) {
    public static MovieInfoDto from(Movie movie) {
        return MovieInfoDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .releaseDate(movie.getReleaseDate())
                .build();
    }
}
