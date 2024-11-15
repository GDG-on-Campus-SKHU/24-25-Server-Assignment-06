package com.example.jwtproject.movie.service;

import com.example.jwtproject.movie.domain.Movie;
import com.example.jwtproject.movie.dto.request.MovieDto;
import com.example.jwtproject.movie.dto.response.MovieInfoDto;
import com.example.jwtproject.movie.repository.MovieRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieService {

    private final MovieRepository movieRepository;

    @Transactional
    public MovieInfoDto saveMovie(MovieDto movieDto) {
        Movie saveMovie = movieRepository.findByTitle(movieDto.title())
                .orElseGet(() -> createMovie(movieDto));

        return MovieInfoDto.from(saveMovie);
    }

    private Movie createMovie(MovieDto movieDto) {

        return movieRepository.save(Movie.builder()
                .title(movieDto.title())
                .genre(movieDto.genre())
                .releaseDate(movieDto.releaseDate())
                .build());
    }

    @Transactional(readOnly = true)
    public List<MovieInfoDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieInfoDto> movieInfoDtos = movies.stream().map(MovieInfoDto::from).toList();

        return movieInfoDtos;
    }
}
