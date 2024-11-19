package com.example.gdg_homework06.repository;

import com.example.gdg_homework06.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
