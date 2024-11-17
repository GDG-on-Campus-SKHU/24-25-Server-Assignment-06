package com.example.jwtapplication.review.controller;

import com.example.jwtapplication.review.domain.Review;
import com.example.jwtapplication.review.dto.ReviewInfoDto;
import com.example.jwtapplication.review.dto.ReviewSaveRequestDto;
import com.example.jwtapplication.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewInfoDto> createReview(Principal principal,
                                                      @RequestBody ReviewSaveRequestDto reviewSaveRequestDto) {
        reviewService.createReview(principal, reviewSaveRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewInfoDto> getReview(Principal principal, @PathVariable Long reviewId) {

        return ResponseEntity.ok(reviewService.getReview(reviewId));
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewInfoDto> updateReview(Principal principal, @PathVariable Long reviewId
                                                        , @RequestBody ReviewInfoDto reviewInfoDto) {

        return ResponseEntity.ok(reviewService.updateReview(principal, reviewId, reviewInfoDto));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ReviewInfoDto> deleteReview(Principal principal, @PathVariable Long reviewId) {
        reviewService.deleteReview(principal, reviewId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
