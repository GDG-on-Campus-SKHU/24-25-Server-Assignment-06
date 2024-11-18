package com.example.sanghwajwt.forum.repository;

import com.example.sanghwajwt.forum.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
