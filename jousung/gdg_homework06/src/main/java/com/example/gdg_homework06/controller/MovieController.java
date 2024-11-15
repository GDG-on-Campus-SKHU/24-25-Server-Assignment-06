package com.example.gdg_homework06.controller;

import com.example.gdg_homework06.dto.movieDto.MovieRequestDto;
import com.example.gdg_homework06.dto.movieDto.MovieResponseDto;
import com.example.gdg_homework06.service.MovieService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieController {

    private final MovieService movieService;

    // 영화 추가
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieResponseDto> addMovie(@RequestBody MovieRequestDto movieRequestDto) {
        MovieResponseDto response = movieService.addMovie(movieRequestDto);
        return ResponseEntity.ok(response);
    }

    // 영화 검색
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDto> getMovie(@PathVariable Long id) {
        MovieResponseDto response = movieService.getMovieById(id);
        return ResponseEntity.ok(response);
    }

    // 영화 수정
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieResponseDto> updateMovie(
            @PathVariable Long id,
            @RequestBody MovieRequestDto movieRequestDto
    ) {
        MovieResponseDto response = movieService.updateMovie(id, movieRequestDto);
        return ResponseEntity.ok(response);
    }

    // 영화 삭제
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
