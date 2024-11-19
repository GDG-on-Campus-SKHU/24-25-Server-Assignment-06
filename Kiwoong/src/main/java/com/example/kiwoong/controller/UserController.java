package com.example.kiwoong.controller;

import com.example.kiwoong.dto.TokenDto;
import com.example.kiwoong.dto.UserInfoDto;
import com.example.kiwoong.dto.UserSignUpDto;
import com.example.kiwoong.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/gdg")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserController {
    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<TokenDto> signUp(@RequestBody UserSignUpDto userSignUpDto) {
        TokenDto response = userService.signUp(userSignUpDto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getUser")
    public ResponseEntity<UserInfoDto> getUser(Principal principal) {
        UserInfoDto userInfoDto = userService.findByPrincipal(principal);

        return ResponseEntity.ok(userInfoDto);
    }

}
