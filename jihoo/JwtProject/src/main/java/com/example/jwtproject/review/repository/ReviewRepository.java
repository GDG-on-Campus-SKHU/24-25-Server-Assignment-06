package com.example.jwtproject.review.repository;

import com.example.jwtproject.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
