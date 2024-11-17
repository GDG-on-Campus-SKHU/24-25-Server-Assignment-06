package com.example.inseok.User.service;

import com.example.inseok.User.domain.Role;
import com.example.inseok.User.domain.User;
import com.example.inseok.User.dto.request.UserLoginDto;
import com.example.inseok.User.dto.request.UserRequestDto;
import com.example.inseok.User.dto.request.UserSignDto;
import com.example.inseok.User.dto.response.TokenDto;
import com.example.inseok.User.dto.response.UserInfoDto;
import com.example.inseok.User.repository.UserRepository;
import com.example.inseok.jwt.TokenProvider;
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
    public UserInfoDto saveUser(UserSignDto userSignDto) {
        // 같은 이메일이 이미 존재하는지 확인
        boolean exists = userRepository.findByEmail(userSignDto.getEmail()).isPresent();
        if (exists) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }
        // 사용자 정보를 저장
        User user = User.builder()
                .email(userSignDto.getEmail())
                .password(passwordEncoder.encode(userSignDto.getPassword())) // 비밀번호 암호화
                .nickName(userSignDto.getNickName())
                .role(Role.ROLE_USER) // 기본 역할 설정
                .build();
        userRepository.save(user);

        return UserInfoDto.from(user);
    }

    @Transactional
    public TokenDto loginUser(UserLoginDto userLoginDto) {
        User findUser = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        String accessToken = tokenProvider.createAccessToken(findUser);
        userRepository.save(findUser);

        return TokenDto.from(accessToken);
    }
    @Transactional(readOnly = true)
    public UserInfoDto getMyInfo(Principal principal) {
        Long userId = Long.parseLong(principal.getName());

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return UserInfoDto.from(findUser);
    }
}

