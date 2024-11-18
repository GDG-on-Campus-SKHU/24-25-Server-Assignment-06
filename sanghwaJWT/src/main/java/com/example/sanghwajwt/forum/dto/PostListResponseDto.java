package com.example.sanghwajwt.forum.dto;

import com.example.sanghwajwt.forum.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PostListResponseDto {
    private List<PostResponseDto> postResponseDtos;

    public static PostListResponseDto from(List<PostResponseDto> postDtos) {
        return PostListResponseDto.builder()
                .postResponseDtos(postDtos)
                .build();
    }
}
