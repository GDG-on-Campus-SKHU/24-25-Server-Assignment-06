package com.example.gdg_homework06.controller;

import com.example.gdg_homework06.dto.reviewDto.ReviewRequestDto;
import com.example.gdg_homework06.dto.reviewDto.ReviewResponseDto;
import com.example.gdg_homework06.service.ReviewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies/{movieId}/reviews")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewController {

    final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReviewResponseDto> createReview(
            @PathVariable Long movieId,
            @RequestBody ReviewRequestDto requestDto
    ) {
        // ContextHolder에서 사용자 ID가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getPrincipal().toString());

        // 리뷰 생성 및 응답 반환
        ReviewResponseDto response = reviewService.createReview(movieId, requestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 리뷰 수정
    @PutMapping("{reviewId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReviewResponseDto> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewRequestDto requestDto
    ) {
        ReviewResponseDto response = reviewService.updateReview(reviewId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 리뷰 삭제
    @DeleteMapping("{reviewId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
