package com.example.gdg_homework06.dto.userDto;

import com.example.gdg_homework06.domain.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access= AccessLevel.PROTECTED)
public class UserSignUpDto {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;

    public static UserSignUpDto from(UserSignUpDto userSignupDto, Role role) {
        return UserSignUpDto.builder()
                .name(userSignupDto.getName())
                .email(userSignupDto.getEmail())
                .password(userSignupDto.getPassword())
                .phoneNumber(userSignupDto.getPassword())
                .role(role).build();
    }

}
