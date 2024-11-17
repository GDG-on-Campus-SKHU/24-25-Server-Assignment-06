package com.example.jwtapplication.review.repository;

import com.example.jwtapplication.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {}