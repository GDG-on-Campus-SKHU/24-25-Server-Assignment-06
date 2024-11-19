package com.example.kiwoong.repository;

import com.example.kiwoong.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
