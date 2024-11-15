package com.example.jwtrefresh.Service;

import com.example.jwtrefresh.Domain.User;
import com.example.jwtrefresh.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String email, String password, String phoneNumber) {
        // 가입시 이메일 중복 확인
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        // 비밀번호 암호화 및 사용자 저장
        String encodedPassword = passwordEncoder.encode(password);
        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .phoneNumber(phoneNumber)
                .build();
        userRepository.save(user);
    }

    public User authenticateUser(String email, String password) {
        // 이메일로 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 틀렸습니다!"));

        // 비밀번호 검증
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 틀렸습니다!");
        }
        return user;
    }
}
