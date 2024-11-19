package com.example.jwtlogin.dto.request;

import lombok.Getter;

@Getter
public class UserSignDto {
    private String userId;
    private String username;
    private String password;
}
