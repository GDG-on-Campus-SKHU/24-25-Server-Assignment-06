package com.example.jwtproject.user.dto.response;

import lombok.Builder;

@Builder
public record TokenDto(
        String accessToken,
        String refreshToken
) {
    public static TokenDto from(String accessToken, String refreshToken) {
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static TokenDto from(String accessToken) {
        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }
}
