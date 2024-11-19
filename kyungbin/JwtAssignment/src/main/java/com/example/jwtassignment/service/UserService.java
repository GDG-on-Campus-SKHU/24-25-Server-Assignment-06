package com.example.jwtassignment.service;

import com.example.jwtassignment.domain.user.User;
import com.example.jwtassignment.dto.UserDto.TokenDto;
import com.example.jwtassignment.dto.UserDto.UserInfoResponseDto;
import com.example.jwtassignment.dto.UserDto.UserLoginRequestDto;
import com.example.jwtassignment.dto.UserDto.UserSignUpRequestDto;
import com.example.jwtassignment.jwt.TokenProvider;
import com.example.jwtassignment.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider TokenProvider;

    // 회원가입
    @Transactional
    public UserInfoResponseDto signUp(UserSignUpRequestDto userSignUpRequestDto) {
        User user = userRepository.save(userSignUpRequestDto.toEntity());
        return UserInfoResponseDto.from(user);
    }

    // 로그인 기능을 수행한다 - 사용자 정보를 검증하고, 유효한 사용자라면 JWT토큰을 생성하여 반환한다.
    @Transactional
    public TokenDto login(UserLoginRequestDto userLoginRequestDto) {
        User user = userRepository.findByUsername(userLoginRequestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        String accessToken = TokenProvider.createAccessToken(user);

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    // 사용자 정보 조회
    @Transactional(readOnly = true)
    public UserInfoResponseDto findByPrincipal(Principal principal) {
        Long Id = Long.parseLong(principal.getName());

        User user = userRepository.findById(Id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return UserInfoResponseDto.from(user);
    }

    @Transactional(readOnly = true)
    public List<UserInfoResponseDto> getAllUsers() {
        List<User> allUsers = userRepository.findAll();

        List<UserInfoResponseDto> userInfoDtos = allUsers.stream()
                .map(UserInfoResponseDto::from)
                .toList();

        return userInfoDtos;
    }
}
