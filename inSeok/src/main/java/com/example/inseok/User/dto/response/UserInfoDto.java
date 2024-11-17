package com.example.inseok.User.dto.response;


import com.example.inseok.User.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInfoDto {
    private String email;
    private String password;
    private String nickName;
    private String role;

    @Builder
    public UserInfoDto(String password, String nickName, String email, String role) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.role = role;
    }

    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .nickName(user.getNickName())
                .role(user.getRole().name())
                .build();
    }
}
