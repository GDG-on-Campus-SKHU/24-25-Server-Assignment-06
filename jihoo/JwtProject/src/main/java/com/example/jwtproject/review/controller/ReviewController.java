package com.example.jwtproject.review.controller;

import com.example.jwtproject.review.dto.request.ReviewRequestDto;
import com.example.jwtproject.review.dto.response.ReviewInfoDto;
import com.example.jwtproject.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewInfoDto> saveReview(Principal principal,
                                                    @RequestBody ReviewRequestDto reviewRequestDto) {
        reviewService.saveReview(principal, reviewRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewInfoDto> getReview(Principal principal, @PathVariable long reviewId) {
        return ResponseEntity.ok().body(reviewService.getReview(reviewId));
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewInfoDto> updateReview(Principal principal, @PathVariable long reviewId
            , @RequestBody ReviewRequestDto reviewRequestDto) {

        return ResponseEntity.ok().body(reviewService.updateReview(principal, reviewId, reviewRequestDto));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ReviewInfoDto> deleteReview(Principal principal, @PathVariable long reviewId) {
        reviewService.deleteReview(principal, reviewId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
