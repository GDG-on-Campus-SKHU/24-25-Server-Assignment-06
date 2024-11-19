package com.example.jwtlogin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BoardInfoDto {
    private Long id;
    private String title;
    private String content;
    private String writerName;
}
