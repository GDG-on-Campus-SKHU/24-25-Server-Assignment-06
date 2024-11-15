package com.example.jwtproject.user.controller;

import com.example.jwtproject.user.dto.request.RefreshTokenDto;
import com.example.jwtproject.user.dto.request.UserLoginDto;
import com.example.jwtproject.user.dto.request.UserSignUpDto;
import com.example.jwtproject.user.dto.response.TokenDto;
import com.example.jwtproject.user.dto.response.UserInfoDto;
import com.example.jwtproject.user.service.UserService;
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

    @PostMapping
    public ResponseEntity<UserInfoDto> signUp(@RequestBody UserSignUpDto userSignUpDto) {
        return ResponseEntity.ok().body(userService.saveUser(userSignUpDto));
    }

    @GetMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok().body(userService.loginUser(userLoginDto));
    }

    @GetMapping
    public ResponseEntity<UserInfoDto> getMyInfo(Principal principal) {
        return ResponseEntity.ok().body(userService.getMyInfo(principal));
    }

    //관리자일때만 전체 회원 조회 가능
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserInfoDto>> getAllUsers(Principal principal) {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        return ResponseEntity.ok().body(userService.refreshAccessToken(refreshTokenDto));
    }
}
