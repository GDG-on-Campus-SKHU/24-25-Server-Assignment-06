package com.example.inseok.User.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserSignDto {
    private String email;
    private String password;
    private String nickName;

}
