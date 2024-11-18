package com.example.sanghwajwt.user.dto;

import lombok.Getter;

@Getter
public class JoinAdminDto {
    private String username;
    private String password;
    private Long adminKey;
}
