package com.example.sanghwajwt.user.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
public class AdminController {
    //admin경로로 오는 API요청은 JWTFilter에 의해 role값이 admin인지 검증받아야 한다.
    @GetMapping("/admin")
    public String adminP() {
        return "admin";
    }
}
