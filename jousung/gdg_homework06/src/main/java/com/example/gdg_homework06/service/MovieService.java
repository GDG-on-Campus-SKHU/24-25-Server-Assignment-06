package com.example.gdg_homework06.service;

import com.example.gdg_homework06.domain.Movie;
import com.example.gdg_homework06.dto.movieDto.MovieRequestDto;
import com.example.gdg_homework06.dto.movieDto.MovieResponseDto;
import com.example.gdg_homework06.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    // 생성
    @Transactional
    public MovieResponseDto addMovie(MovieRequestDto movieRequestDto) {
        Movie movie = Movie.builder()
                .title(movieRequestDto.getTitle())
                .director(movieRequestDto.getDirector())
                .build();

        movieRepository.save(movie);
        return MovieResponseDto.from(movie);
    }

    // id로 조회
    @Transactional(readOnly = true)
    public MovieResponseDto getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));

        return MovieResponseDto.from(movie);
    }

    // 수정
    @Transactional
    public MovieResponseDto updateMovie(Long id, MovieRequestDto movieRequestDto) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));

        movie.updateField(movieRequestDto);
        movieRepository.save(movie);
        return MovieResponseDto.from(movie);
    }

    // 삭제
    @Transactional
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

}
