package com.example.kiwoong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminDto {
    private String email;
    private String password;
    private String phoneNumber;
    private String authCode;
}
