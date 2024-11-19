package com.example.kiwoong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoDto {
    private String email;
    private String phoneNumber;
    private String role;
}
