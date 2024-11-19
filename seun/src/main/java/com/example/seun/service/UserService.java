package com.example.seun.service;

import com.example.seun.dto.UserInfoDto;
import com.example.seun.dto.UserSignupDto;
import com.example.seun.entity.User;
import com.example.seun.repository.UserRepository;
import com.example.seun.jwt.TokenProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public UserInfoDto signUp(UserSignupDto userSignupDto) {
        User user = userRepository.save(User.builder()
                .username(userSignupDto.getUsername())
                .password(passwordEncoder.encode(userSignupDto.getPassword()))
                .email(userSignupDto.getEmail())
                .build());

        return UserInfoDto.from(user);
    }

    @Transactional(readOnly = true)
    public UserInfoDto findByToken(String token) {
        Long userId = tokenProvider.getUserIdFromToken(token);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return UserInfoDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Transactional
    public boolean authenticateUser(String email, String password) {
        return Optional.ofNullable(userRepository.findByEmail(email))
                .map(user -> passwordEncoder.matches(password, user.get().getPassword()))
                .orElse(false);
    }

    @Transactional
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + id));
        userRepository.delete(user);
    }
}
