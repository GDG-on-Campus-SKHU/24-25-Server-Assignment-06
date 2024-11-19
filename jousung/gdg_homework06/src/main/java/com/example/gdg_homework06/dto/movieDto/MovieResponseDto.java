package com.example.gdg_homework06.dto.movieDto;

import com.example.gdg_homework06.domain.Movie;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MovieResponseDto {
    private Long id;
    private String title;
    private String director;

    public static MovieResponseDto from(Movie movie) {
        return MovieResponseDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .director(movie.getDirector())
                .build();

    }

}
