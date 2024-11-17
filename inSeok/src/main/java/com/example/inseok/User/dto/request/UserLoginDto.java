package com.example.inseok.User.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserLoginDto {
    private final String email;
    private final String pwd;
}
