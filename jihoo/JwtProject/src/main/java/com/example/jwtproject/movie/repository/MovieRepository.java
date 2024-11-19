package com.example.jwtproject.movie.repository;

import com.example.jwtproject.movie.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTitle(String Title);
}
