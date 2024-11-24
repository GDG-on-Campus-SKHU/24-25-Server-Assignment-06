package net.skhu.bomin.controller;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.skhu.bomin.dto.request.UserSignUpDto;
import net.skhu.bomin.dto.response.TokenDto;
import net.skhu.bomin.dto.response.UserInfoDto;
import net.skhu.bomin.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/gdg")
public class UserController {
    private final UserService userService;

    //사용자 생성
    @PostMapping("/signUp")
    public ResponseEntity<TokenDto> signUpUser(@RequestBody UserSignUpDto us){
        TokenDto response = userService.signUp(us);
        return ResponseEntity.ok(response);
    }

    //관리자 생성
    @PostMapping("/admin")
    public ResponseEntity<TokenDto> admin(@RequestBody UserSignUpDto us){
        TokenDto response = userService.admin(us);
        return ResponseEntity.ok(response);
    }

    //사용자,관리자 조회
    @GetMapping("/getUser")
    public ResponseEntity<UserInfoDto> getUser(Principal principal){
        UserInfoDto userInfoDto = userService.findByPrincipal(principal);
        return ResponseEntity.ok(userInfoDto);
    }
}