package com.example.gdg_homework06.service;

import com.example.gdg_homework06.domain.Movie;
import com.example.gdg_homework06.domain.Review;
import com.example.gdg_homework06.domain.User;
import com.example.gdg_homework06.dto.reviewDto.ReviewRequestDto;
import com.example.gdg_homework06.repository.MovieRepository;
import com.example.gdg_homework06.repository.ReviewRepository;
import com.example.gdg_homework06.dto.reviewDto.ReviewResponseDto;
import com.example.gdg_homework06.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    // 리뷰 등록
    @Transactional
    public ReviewResponseDto createReview(Long movieId, ReviewRequestDto requestDto, Long userId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Review review = Review.builder()
                .movie(movie)
                .user(user)
                .score(requestDto.getScore())
                .build();

        Review savedReview = reviewRepository.save(review);
        return ReviewResponseDto.of(savedReview);
    }

    // 리뷰 수정
    @Transactional
    public ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto reviewRequestDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        review.updateField(reviewRequestDto);
        return ReviewResponseDto.of(review);
    }

    // 리뷰 삭제
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

}
