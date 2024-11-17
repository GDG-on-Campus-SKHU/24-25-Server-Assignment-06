package com.example.jwtapplication.controller;


import com.example.jwtapplication.dto.TokenDto;
import com.example.jwtapplication.dto.UserInfoDto;
import com.example.jwtapplication.dto.UserLoginDto;
import com.example.jwtapplication.dto.UserSignUpDto;
import com.example.jwtapplication.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<UserInfoDto> signUp(@RequestBody UserSignUpDto userSignUpDto) {
        UserInfoDto userInfoDto = userService.signUp(userSignUpDto);
        return ResponseEntity.ok(userInfoDto);
    }

    @GetMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserLoginDto userLoginDto) {
        TokenDto response = userService.login(userLoginDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserInfoDto>> getAllUsers(Principal principal) {

        return ResponseEntity.ok(userService.getAllUsers());
    }
}