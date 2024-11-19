package com.example.seun.dto;

import com.example.seun.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoDto {
    private String username;
    private String email;
    private String role;

    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
