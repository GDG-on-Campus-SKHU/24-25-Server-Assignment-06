package com.example.jwtproject.user.service;

import com.example.jwtproject.user.domain.Role;
import com.example.jwtproject.user.domain.User;
import com.example.jwtproject.user.dto.request.RefreshTokenDto;
import com.example.jwtproject.user.dto.request.UserLoginDto;
import com.example.jwtproject.user.dto.request.UserSignUpDto;
import com.example.jwtproject.user.dto.response.TokenDto;
import com.example.jwtproject.user.dto.response.UserInfoDto;
import com.example.jwtproject.jwt.TokenProvider;
import com.example.jwtproject.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public UserInfoDto saveUser(UserSignUpDto userSignUpDto) {
        User user = userRepository.findByEmail(userSignUpDto.email())
                .orElseGet(() -> createUser(userSignUpDto));

        return UserInfoDto.from(user);
    }

    @Transactional
    public TokenDto loginUser(UserLoginDto userLoginDto) {
        User findUser = userRepository.findByEmail(userLoginDto.email())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        String accessToken = tokenProvider.createAccessToken(findUser);
        String refreshToken = tokenProvider.createRefreshToken(findUser);

        findUser.updateRefreshToken(refreshToken);

        userRepository.save(findUser);

        return TokenDto.from(accessToken, refreshToken);
    }

    @Transactional(readOnly = true)
    public UserInfoDto getMyInfo(Principal principal) {
        Long userId = Long.parseLong(principal.getName());

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return UserInfoDto.from(findUser);
    }

    @Transactional
    public TokenDto refreshAccessToken(RefreshTokenDto refreshTokenDto) {
        if(!tokenProvider.validateToken(refreshTokenDto.refreshToken())) {
            throw new IllegalArgumentException("잘못된 토큰입니다.");
        }

        Long userId = tokenProvider.getIdFromRefreshToken(refreshTokenDto.refreshToken());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        String newAccessToken = tokenProvider.createAccessToken(user);

        return TokenDto.from(newAccessToken);
    }

    @Transactional(readOnly = true)
    public List<UserInfoDto> getAllUsers() {
        List<User> allUsers = userRepository.findAll();

        List<UserInfoDto> userInfoDtos = allUsers.stream()
                .map(UserInfoDto::from)
                .toList();

        return userInfoDtos;
    }

    private User createUser(UserSignUpDto userSignUpDto) {

        return  userRepository.save(User.builder()
                .username(userSignUpDto.username())
                .email(userSignUpDto.email())
                .pwd(passwordEncoder.encode(userSignUpDto.pwd()))
                .role(Role.ROLE_USER)
                .build());
    }
}
