package com.example.inseok.User.controller;

import com.example.inseok.User.dto.request.UserLoginDto;
import com.example.inseok.User.dto.request.UserSignDto;
import com.example.inseok.User.dto.response.TokenDto;
import com.example.inseok.User.dto.response.UserInfoDto;
import com.example.inseok.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<UserInfoDto> signUp(@RequestBody UserSignDto userSignDto) {
        return ResponseEntity.ok().body(userService.saveUser(userSignDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok().body(userService.loginUser(userLoginDto));
    }

    @GetMapping
    public ResponseEntity<UserInfoDto> getMyInfo(Principal principal) {
        return ResponseEntity.ok().body(userService.getMyInfo(principal));
    }

}
