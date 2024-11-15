package com.example.jwtproject.user.dto.response;

import com.example.jwtproject.user.domain.User;
import lombok.Builder;

@Builder
public record UserInfoDto(
        Long id,
        String username,
        String email,
        String role
) {
    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
