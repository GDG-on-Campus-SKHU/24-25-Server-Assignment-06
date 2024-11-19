package com.example.jwtproject.user.dto.request;

public record UserSignUpDto(
        String email,
        String pwd,
        String username
) {
}
