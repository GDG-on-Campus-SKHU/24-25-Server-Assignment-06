package com.example.inseok.noticeBoard.dto.response;

import com.example.inseok.noticeBoard.domain.NoticeBoard;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NoticeBoardInfoDto {
    Long noticeBoardId;
    String nickName;
    String title;
    String content;
    String time;

    public static NoticeBoardInfoDto from(NoticeBoard noticeBoard) {
        return NoticeBoardInfoDto.builder()
                .noticeBoardId(noticeBoard.getId())
                .nickName(noticeBoard.getUser().getNickName())
                .title(noticeBoard.getTitle())
                .content(noticeBoard.getContents())
                .time(noticeBoard.getTime())
                .build();
    }
}
