package com.example.kiwoong.service;

import com.example.kiwoong.domain.Admin;
import com.example.kiwoong.domain.User;
import com.example.kiwoong.domain.Post;
import com.example.kiwoong.dto.AdminDto;
import com.example.kiwoong.dto.TokenDto;
import com.example.kiwoong.jwt.TokenProvider;
import com.example.kiwoong.repository.AdminRepository;
import com.example.kiwoong.repository.UserRepository;
import com.example.kiwoong.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public TokenDto signUp(AdminDto adminDto) {
        // 인증코드가 123인 경우에만 ADMIN 등록
        if (!"123".equals(adminDto.getAuthCode())) {
            throw new IllegalArgumentException("Invalid authorization code.");
        }

        Admin admin = Admin.builder()
                .email(adminDto.getEmail())
                .password(adminDto.getPassword())
                .phoneNumber(adminDto.getPhoneNumber())
                .authCode(adminDto.getAuthCode())
                .role("ADMIN")
                .build();

        Admin savedAdmin = adminRepository.save(admin);

        String token = tokenProvider.createAccessToken(savedAdmin);

        return new TokenDto(token);
    }

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 사용자 삭제
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // 게시글 조회
    @Transactional(readOnly = true)
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Post findPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
