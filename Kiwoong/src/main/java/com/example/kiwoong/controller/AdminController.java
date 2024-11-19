package com.example.kiwoong.controller;

import com.example.kiwoong.domain.User;
import com.example.kiwoong.domain.Post;
import com.example.kiwoong.dto.AdminDto;
import com.example.kiwoong.dto.TokenDto;
import com.example.kiwoong.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/signUp")
    public ResponseEntity<TokenDto> signUp(@RequestBody AdminDto adminDto) {
        TokenDto response = adminService.signUp(adminDto);

        return ResponseEntity.ok(response);
    }

    // 모든 사용자 조회
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = adminService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    // 특정 사용자 조회
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = adminService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    // 사용자 삭제
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // 모든 게시글 조회
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = adminService.findAllPosts();
        return ResponseEntity.ok(posts);
    }

    // 특정 게시글 조회
    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        Post post = adminService.findPostById(id);
        return ResponseEntity.ok(post);
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        adminService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
