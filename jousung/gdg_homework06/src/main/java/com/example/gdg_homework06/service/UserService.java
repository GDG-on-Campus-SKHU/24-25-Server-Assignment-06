package com.example.gdg_homework06.service;

import com.example.gdg_homework06.domain.User;
import com.example.gdg_homework06.dto.userDto.TokenDto;
import com.example.gdg_homework06.dto.userDto.UserInfoDto;
import com.example.gdg_homework06.dto.userDto.UserSignUpDto;
import com.example.gdg_homework06.jwt.TokenProvider;
import com.example.gdg_homework06.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    // 일반 사용자 회원가입
    @Transactional
    public TokenDto signUp(UserSignUpDto signUpDto) {
        User user = userRepository.save(User.builder()
                .name(signUpDto.getName())
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .phoneNumber(signUpDto.getPhoneNumber())
                .role(signUpDto.getRole())
                .build());

        String accessToken = tokenProvider.createAccessToken(user);

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    // 관리자 회원가입
    @Transactional
    public TokenDto signUpAdmin(UserSignUpDto signUpDto) {
        User user = userRepository.save(User.builder()
                .name(signUpDto.getName())
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .phoneNumber(signUpDto.getPhoneNumber())
                .role(signUpDto.getRole())
                .build());

        String accessToken = tokenProvider.createAccessToken(user);

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    @Transactional(readOnly = true)
    public UserInfoDto findByPrincipal(Principal principal) {
        Long userId = Long.parseLong(principal.getName());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return UserInfoDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().name())
                .build();
    }
}
