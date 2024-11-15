package com.example.gdg_homework06.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
}
