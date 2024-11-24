package com.example.tripplanner.service;

import com.example.tripplanner.domain.User;
import com.example.tripplanner.dto.TokenDto;
import com.example.tripplanner.dto.UserInfoDto;
import com.example.tripplanner.dto.UserLoginDto;
import com.example.tripplanner.dto.UserSignUpDto;
import com.example.tripplanner.jwt.TokenProvider;
import com.example.tripplanner.repository.UserRepository;
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
    public UserInfoDto signUp(UserSignUpDto signUpDto) {
        User user = userRepository.save(signUpDto.toEntity());
        return UserInfoDto.from(user);
    }

    @Transactional
    public TokenDto login(UserLoginDto userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        String accessToken = tokenProvider.createAccessToken(user);

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    @Transactional(readOnly = true)
    public UserInfoDto findByPrincipal(Principal principal) {
        Long Id = Long.parseLong(principal.getName());

        User user = userRepository.findById(Id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return UserInfoDto.from(user);
    }
}
