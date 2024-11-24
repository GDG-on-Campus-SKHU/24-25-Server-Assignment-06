package com.example.tripplanner.controller;

import com.example.tripplanner.dto.TokenDto;
import com.example.tripplanner.dto.UserInfoDto;
import com.example.tripplanner.dto.UserLoginDto;
import com.example.tripplanner.dto.UserSignUpDto;
import com.example.tripplanner.jwt.TokenProvider;
import com.example.tripplanner.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signUp")
    public ResponseEntity<UserInfoDto> signUp(@RequestBody UserSignUpDto userSignUpDto){
        UserInfoDto userInfoDto = userService.signUp(userSignUpDto);
        return ResponseEntity.ok(userInfoDto);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserLoginDto userLoginDto){
        TokenDto tokenDto = userService.login(userLoginDto);
        return ResponseEntity.ok(tokenDto);
    }

    @GetMapping("/getUser")
    public ResponseEntity<UserInfoDto> getUser(Principal principal){
        UserInfoDto userInfoDto = userService.findByPrincipal(principal);
        return ResponseEntity.ok(userInfoDto);
    }

}
