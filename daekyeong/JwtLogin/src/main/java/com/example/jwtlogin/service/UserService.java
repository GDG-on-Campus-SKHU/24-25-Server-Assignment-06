package com.example.jwtlogin.service;

import com.example.jwtlogin.domain.Role;
import com.example.jwtlogin.repository.UserRepository;
import com.example.jwtlogin.domain.User;
import com.example.jwtlogin.dto.token.TokenDto;
import com.example.jwtlogin.dto.response.UserInfoDto;
import com.example.jwtlogin.dto.request.UserSignDto;
import com.example.jwtlogin.jwt.TokenProvider;
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

    @Transactional
    public TokenDto signUp(UserSignDto userSignDto) {
        if(userRepository.findById(userSignDto.getUserId()).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 ID입니다.");
        }

        User user = userRepository.save(User.builder()
                .userId(userSignDto.getUserId())
                .userName(userSignDto.getUsername())
                .password(passwordEncoder.encode(userSignDto.getPassword()))
                .role(Role.ROLE_USER)
                .build());

        String accessToken = tokenProvider.createAccessToken(user);

        return TokenDto.builder()
                .token(accessToken)
                .build();
    }

    @Transactional(readOnly = true)
    public UserInfoDto findByPrincipal(Principal principal) {
        String userId = principal.getName();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));

        return UserInfoDto.builder()
                .userId(user.getUserId())
                .username(user.getUserName())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    @Transactional
    public TokenDto mkadmin(UserSignDto userSignDto) {
        User user = userRepository.save(User.builder()
                .userId(userSignDto.getUserId())
                .userName(userSignDto.getUsername())
                .password(passwordEncoder.encode(userSignDto.getPassword()))
                .role(Role.ROLE_ADMIN)
                .build());

        String accessToken = tokenProvider.createAccessToken(user);

        return TokenDto.builder()
                .token(accessToken)
                .build();
    }
}
