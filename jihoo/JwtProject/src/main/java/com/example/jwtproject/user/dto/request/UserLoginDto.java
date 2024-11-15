package com.example.jwtproject.user.dto.request;

public record UserLoginDto(
        String email,
        String pwd
) {
}
