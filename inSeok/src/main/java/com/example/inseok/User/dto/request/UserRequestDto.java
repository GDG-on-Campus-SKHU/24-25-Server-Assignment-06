package com.example.inseok.User.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserRequestDto {
    Long UserId;
    String password;
    String nickName;
    String email;
}
