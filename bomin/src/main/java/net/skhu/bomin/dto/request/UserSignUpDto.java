package net.skhu.bomin.dto.request;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class UserSignUpDto {
    private String email;
    private String pw;
    private String phoneNumber;
    private String name;
    private String role;
}
