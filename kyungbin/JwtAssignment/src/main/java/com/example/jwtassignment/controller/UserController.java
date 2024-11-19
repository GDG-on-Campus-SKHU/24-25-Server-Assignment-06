package com.example.jwtassignment.controller;

import com.example.jwtassignment.dto.UserDto.TokenDto;
import com.example.jwtassignment.dto.UserDto.UserInfoResponseDto;
import com.example.jwtassignment.dto.UserDto.UserLoginRequestDto;
import com.example.jwtassignment.dto.UserDto.UserSignUpRequestDto;
import com.example.jwtassignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping
    public ResponseEntity<UserInfoResponseDto> signUp(@RequestBody UserSignUpRequestDto userSignUpRequestDto) {
        UserInfoResponseDto response = userService.signUp(userSignUpRequestDto);
        return ResponseEntity.ok(response);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        TokenDto token = userService.login(userLoginRequestDto);
        return ResponseEntity.ok(token);
    }

    // 사용자 정보 조회
    @GetMapping("/me")
    public ResponseEntity<UserInfoResponseDto> getUserInfo(Principal principal) {
        UserInfoResponseDto userInfo = userService.findByPrincipal(principal);
        return ResponseEntity.ok(userInfo);
    }

    // 전체 사용자 조회
    @GetMapping("/list")
    public ResponseEntity<List<UserInfoResponseDto>> getAllUsers(Principal principal) {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }
}
