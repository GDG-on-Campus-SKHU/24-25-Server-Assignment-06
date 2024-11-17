package com.example.tripplanner.dto;

import com.example.tripplanner.domain.Role;
import com.example.tripplanner.domain.Trip;
import com.example.tripplanner.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoDto {
    private Long id;
    private String email;
    private String phoneNumber;
    private String role;

    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().name())
                .build();
    }
}
