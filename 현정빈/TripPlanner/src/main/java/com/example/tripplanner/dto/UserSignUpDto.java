package com.example.tripplanner.dto;

import com.example.tripplanner.domain.Role;
import com.example.tripplanner.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserSignUpDto { //requestDto
    private String email;
    private String password;
    private String phoneNumber;

    public User toEntity() {
        return User.builder()
                .password(password)
                .email(email)
                .phoneNumber(phoneNumber)
                .role(Role.ROLE_USER)
                .build();
    }
}
