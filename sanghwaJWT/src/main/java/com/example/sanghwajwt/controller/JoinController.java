package com.example.sanghwajwt.controller;

import com.example.sanghwajwt.dto.JoinDto;
import com.example.sanghwajwt.service.JoinService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    //reponseEntity 해야됨
    public String signUp(JoinDto joinDto) {
        joinService.joinProcess(joinDto);
        return "ok";
    }
}
