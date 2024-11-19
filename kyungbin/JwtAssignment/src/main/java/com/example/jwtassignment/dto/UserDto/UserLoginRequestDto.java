    package com.example.jwtassignment.dto.UserDto;

    import com.example.jwtassignment.domain.user.User;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Getter;

    @Getter
    @Builder
    @AllArgsConstructor
    public class UserLoginRequestDto {
        private String username;
        private String password;

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .build();
        }

    }
