package com.example.jwtassignment.dto.UserDto;

import com.example.jwtassignment.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserSignUpRequestDto {
    private String email;
    private String username;
    private String password;

    public User toEntity() {
        return User.builder()
                .email(email)
                .username(username)
                .password(password)
                .build();
    }

}
