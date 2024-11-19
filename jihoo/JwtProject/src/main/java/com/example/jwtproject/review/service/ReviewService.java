package com.example.jwtproject.review.service;

import com.example.jwtproject.movie.domain.Movie;
import com.example.jwtproject.review.domain.Review;
import com.example.jwtproject.user.domain.User;
import com.example.jwtproject.review.dto.request.ReviewRequestDto;
import com.example.jwtproject.review.dto.response.ReviewInfoDto;
import com.example.jwtproject.movie.repository.MovieRepository;
import com.example.jwtproject.review.repository.ReviewRepository;
import com.example.jwtproject.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.Principal;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MovieRepository adminMovieRepository;

    @Transactional
    public ReviewInfoDto saveReview(Principal principal, ReviewRequestDto reviewRequestDto) {
        Long userId = Long.parseLong(principal.getName());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Movie movie = adminMovieRepository.findById(reviewRequestDto.movieId())
                .orElseThrow(() -> new IllegalArgumentException("영화가 존재하지 않습니다"));

        Review saveReview = reviewRepository.save(Review.builder()
                .movie(movie)
                .user(user)
                .comment(reviewRequestDto.comment())
                .rating(reviewRequestDto.rating())
                .build());

        return ReviewInfoDto.from(saveReview);
    }

    @Transactional(readOnly = true)
    public ReviewInfoDto getReview(long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

        return ReviewInfoDto.from(review);
    }

    @Transactional
    public ReviewInfoDto updateReview(Principal principal,long reviewId, ReviewRequestDto reviewRequestDto) {

        Long userId = Long.parseLong(principal.getName());

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

        if (review.getUser().getId() != userId) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        review.update(reviewRequestDto);

        return ReviewInfoDto.from(review);
    }

    @Transactional
    public void deleteReview(Principal principal, long reviewId) {
        Long userId = Long.parseLong(principal.getName());

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

        if (review.getUser().getId() != userId) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        reviewRepository.delete(review);
    }
}
