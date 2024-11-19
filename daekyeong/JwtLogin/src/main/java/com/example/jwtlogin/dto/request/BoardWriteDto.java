package com.example.jwtlogin.dto.request;

import com.example.jwtlogin.domain.User;
import lombok.Getter;

@Getter
public class BoardWriteDto {
    private String title;
    private String content;
}
