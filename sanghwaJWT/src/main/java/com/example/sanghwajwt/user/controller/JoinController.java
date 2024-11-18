package com.example.sanghwajwt.user.controller;

import com.example.sanghwajwt.user.dto.JoinAdminDto;
import com.example.sanghwajwt.user.dto.JoinDto;
import com.example.sanghwajwt.user.sevice.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {
    //    1. 회원가입시 username/password를 전송하면 JoinService단에서 data.setRole("ROLE_ADMIN"); 구현으로 인하여 ADMIN 권한이 해당 아이디에게 적용된 상태로 DB에 저장된다.
//    2. 로그인시 username/password를 전송하면 DB에서 해당 유저를 찾아 검증한 뒤 JWT를 발급해주는데 우리가 설정한 코드에서 JWT 내부 payload에 ADMIN이라는 값이 들어있음.
//    3. 앞으로 모든 경로 요청에 JWT를 보내줄 때 JWTFitler에서 토큰에 들어 있는 role 값을 꺼내어 인증을 진행하기 때문에 해당 role 값을 획득.
    private final JoinService joinService;

    @PostMapping("/join")

    public String signUp(@RequestBody JoinDto joinDto) {
        joinService.joinProcess(joinDto);
        return "ok";
    }

    @PostMapping("/join/admin")
    public String signUpAdmin(@RequestBody JoinAdminDto joinAdminDto) {
        joinService.joinAdminProcess(joinAdminDto);
        return "ok";
    }
}
