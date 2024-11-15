package com.example.jwtproject.movie.controller;

import com.example.jwtproject.movie.dto.request.MovieDto;
import com.example.jwtproject.movie.dto.response.MovieInfoDto;
import com.example.jwtproject.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    //관리자일때만 영화 정보 등록 가능
    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieInfoDto> saveMovie(@RequestBody MovieDto movieDto) {
        return ResponseEntity.ok().body(movieService.saveMovie(movieDto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<MovieInfoDto>> getAllMovies(Principal principal) {
        List<MovieInfoDto> allMovies = movieService.getAllMovies();

        return ResponseEntity.ok().body(allMovies);
    }
}
