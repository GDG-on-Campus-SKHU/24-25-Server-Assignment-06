package com.example.sanghwajwt.forum.service;

import com.example.sanghwajwt.forum.dto.PostListResponseDto;
import com.example.sanghwajwt.forum.dto.PostResponseDto;
import com.example.sanghwajwt.forum.dto.PostSaveDto;
import com.example.sanghwajwt.forum.entity.Post;
import com.example.sanghwajwt.forum.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public PostResponseDto save(PostSaveDto postSaveDto) {
        Post post = postSaveDto.toEntity();
        postRepository.save(post);
        return PostResponseDto.from(post);
    }

    @Transactional
    public PostListResponseDto findAllPosts(){
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> postResponseDtos = posts.stream()
                .map(PostResponseDto::from)
                .toList();
        return PostListResponseDto.from(postResponseDtos);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostSaveDto postSaveDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        post.update(postSaveDto);
        return PostResponseDto.from(post);
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
