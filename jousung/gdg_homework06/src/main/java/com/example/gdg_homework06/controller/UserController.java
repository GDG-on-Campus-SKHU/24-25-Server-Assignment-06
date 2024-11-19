package com.example.gdg_homework06.controller;

import com.example.gdg_homework06.domain.Role;
import com.example.gdg_homework06.dto.userDto.TokenDto;
import com.example.gdg_homework06.dto.userDto.UserInfoDto;
import com.example.gdg_homework06.dto.userDto.UserSignUpDto;
import com.example.gdg_homework06.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/gdg")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 유저 회원가입
    @PostMapping("/signUp")
    public ResponseEntity<TokenDto> signUp(@RequestBody UserSignUpDto userSignUpDto) {

        UserSignUpDto userDto = UserSignUpDto.from(userSignUpDto,Role.ROLE_USER);
        TokenDto response = userService.signUp(userDto);

        return ResponseEntity.ok(response);
    }

    // 관리자 회원가입
    @PostMapping("/admin/signUp")
    public ResponseEntity<TokenDto> signUpAdmin(@RequestBody UserSignUpDto userSignUpDto) {

        UserSignUpDto userDto = UserSignUpDto.from(userSignUpDto,Role.ROLE_ADMIN);
        TokenDto response = userService.signUp(userDto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getUser")
    public ResponseEntity<UserInfoDto> getUser(Principal principal) {
        UserInfoDto userInfoDto = userService.findByPrincipal(principal);
        return ResponseEntity.ok(userInfoDto);
    }
}
