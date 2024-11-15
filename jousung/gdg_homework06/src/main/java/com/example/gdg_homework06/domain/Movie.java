package com.example.gdg_homework06.domain;

import com.example.gdg_homework06.dto.movieDto.MovieRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String director;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public void updateField(MovieRequestDto movieRequestDto) {
        this.title = movieRequestDto.getTitle();
        this.director = movieRequestDto.getDirector();
    }

    @Builder
    public Movie(String title, String director) {
        this.title = title;
        this.director = director;
    }
}
