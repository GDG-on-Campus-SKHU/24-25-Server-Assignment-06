package com.example.jwtapplication.service;


import com.example.jwtapplication.domain.User;
import com.example.jwtapplication.dto.TokenDto;
import com.example.jwtapplication.dto.UserInfoDto;
import com.example.jwtapplication.dto.UserLoginDto;
import com.example.jwtapplication.dto.UserSignUpDto;
import com.example.jwtapplication.jwt.TokenProvider;
import com.example.jwtapplication.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public UserInfoDto signUp(UserSignUpDto signUpDto) {
        User user = userRepository.save(User.builder()
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .username(signUpDto.getUsername())
                .build());

        return UserInfoDto.builder()
                .email(user.getEmail())
                .role(user.getRole().name())
                .username(user.getUsername())
                .build();
    }

    @Transactional
    public TokenDto login(UserLoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("사용자를 찾을 수 없습니다."));


        String accessToken = tokenProvider.createAccessToken(user);

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    @Transactional(readOnly = true)
    public List<UserInfoDto> getAllUsers() {
        List<User> allUsers = userRepository.findAll();

        List<UserInfoDto> userInfoDtos = allUsers.stream()
                .map(UserInfoDto::fromUser)
                .toList();

        return userInfoDtos;
    }
}