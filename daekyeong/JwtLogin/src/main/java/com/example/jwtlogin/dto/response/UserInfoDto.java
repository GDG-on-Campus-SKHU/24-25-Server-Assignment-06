package com.example.jwtlogin.dto.response;

import com.example.jwtlogin.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoDto {
    private String userId;
    private String username;
    private String password;
    private Role role;
}
