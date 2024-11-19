package com.example.jwtlogin.controller;

import com.example.jwtlogin.dto.token.TokenDto;
import com.example.jwtlogin.dto.response.UserInfoDto;
import com.example.jwtlogin.dto.request.UserSignDto;
import com.example.jwtlogin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<TokenDto> signUp(@RequestBody UserSignDto userSignDto) {
        TokenDto token = userService.signUp(userSignDto);

        return ResponseEntity.ok(token);
    }
    @GetMapping("/getUser")
    public ResponseEntity<UserInfoDto> getUser(Principal principal) {
        UserInfoDto userInfoDto = userService.findByPrincipal(principal);

        return ResponseEntity.ok(userInfoDto);
    }

    //어드민 권한 부여기능 임시로 설정
    @PostMapping("/mkadmin")
    public ResponseEntity<TokenDto> mkAdmin(@RequestBody UserSignDto userSignDto) {
        TokenDto token = userService.mkadmin(userSignDto);

        return ResponseEntity.ok(token);
    }
}
