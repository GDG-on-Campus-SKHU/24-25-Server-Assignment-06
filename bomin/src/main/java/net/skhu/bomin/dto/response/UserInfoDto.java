package net.skhu.bomin.dto.response;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoDto {
    private String email;
    private String phoneNumber;
    private String name;
    private String role;
}