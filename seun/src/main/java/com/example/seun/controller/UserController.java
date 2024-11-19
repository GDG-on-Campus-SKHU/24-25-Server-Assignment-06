package com.example.seun.controller;

import com.example.seun.dto.UserInfoDto;
import com.example.seun.dto.UserSignupDto;
import com.example.seun.entity.User;
import com.example.seun.jwt.TokenProvider;
import com.example.seun.repository.UserRepository;
import com.example.seun.service.JwtBlacklistService;
import com.example.seun.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final JwtBlacklistService blacklistService;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<UserInfoDto> signUp(@RequestBody UserSignupDto userSignupDto) {
        UserInfoDto response = userService.signUp(userSignupDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserSignupDto userSignupDto) {
        User user = userRepository.findByEmail(userSignupDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 이메일 또는 비밀번호입니다."));

        if (!userService.authenticateUser(userSignupDto.getEmail(), userSignupDto.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "잘못된 이메일 또는 비밀번호입니다."));
        }

        String accessToken = tokenProvider.createAccessToken(user);
        String refreshToken = tokenProvider.createRefreshToken(user);

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshAccessToken(@RequestParam("refreshToken") String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "유효하지 않거나 만료된 리프레시 토큰입니다."));
        }

        String newAccessToken = tokenProvider.createAccessTokenFromRefreshToken(refreshToken);
        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }

    @GetMapping("/getUser")
    public ResponseEntity<UserInfoDto> getUser(HttpServletRequest request) {
        String token = tokenProvider.resolveToken(request);

        if (token == null || !tokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        UserInfoDto userInfoDto = userService.findByToken(token);
        return ResponseEntity.ok(userInfoDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = tokenProvider.resolveToken(request);
        if (token != null && tokenProvider.validateToken(token)) {
            blacklistService.blacklistToken(token);
            return ResponseEntity.ok("로그아웃 성공");
        } else {
            return ResponseEntity.badRequest().body("잘못된 토큰 또는 만료된 토큰입니다.");
        }
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<String> deleteAccount(HttpServletRequest request) {
        String token = tokenProvider.resolveToken(request);
        if (token != null && tokenProvider.validateToken(token)) {
            blacklistService.blacklistToken(token);
            Long userId = tokenProvider.getUserIdFromToken(token);
            userService.deleteUserById(userId);
            return ResponseEntity.ok("계정 삭제 성공");
        } else {
            return ResponseEntity.badRequest().body("잘못된 토큰 또는 만료된 토큰입니다.");
        }
    }
}
