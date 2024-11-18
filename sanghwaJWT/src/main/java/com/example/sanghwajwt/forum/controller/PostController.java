package com.example.sanghwajwt.forum.controller;

import com.example.sanghwajwt.forum.dto.PostListResponseDto;
import com.example.sanghwajwt.forum.dto.PostResponseDto;
import com.example.sanghwajwt.forum.dto.PostSaveDto;
import com.example.sanghwajwt.forum.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    //게시글 작성
    @PostMapping
    public ResponseEntity<PostResponseDto> save(@RequestBody PostSaveDto postSaveDto) {
        return new ResponseEntity<>(postService.save(postSaveDto), HttpStatus.OK);
    }

    //게시글 전체 조회 - admin 전용
    @GetMapping("/allPost")
    public ResponseEntity<PostListResponseDto> findAll() {
        return new ResponseEntity<>(postService.findAllPosts(), HttpStatus.OK);
    }

    //게시글 수정 - User의 ID를 검증해서 본인의 게시글만 수정 가능하게끔 해야한다, 일단 모두에게 열어두기
    @PatchMapping("/{id}")
    public ResponseEntity<PostResponseDto> updateById(@PathVariable Long id, @RequestBody PostSaveDto postSaveDto) {
        return new ResponseEntity<>(postService.updatePost(id, postSaveDto), HttpStatus.OK);
    }

    //게시글 삭제 - admin 전용
    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
