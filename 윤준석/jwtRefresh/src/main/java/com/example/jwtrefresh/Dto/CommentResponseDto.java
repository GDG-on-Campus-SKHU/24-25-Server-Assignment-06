package com.example.jwtrefresh.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
    private Long id;        // 댓글 ID
    private String content; // 댓글 내용
    private Long userId;    // 작성자 ID
}
