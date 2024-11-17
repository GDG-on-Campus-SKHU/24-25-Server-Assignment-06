package com.example.inseok.noticeBoard.domain;

import com.example.inseok.User.domain.User;
import com.example.inseok.noticeBoard.dto.request.NoticeBoardRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class NoticeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "time", nullable = false)
    private String time;

    @Builder
    public NoticeBoard(User user, String title, String contents, String time) {
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.time = time;
    }

    public void update(NoticeBoardRequestDto noticeBoardRequestDto) {
        this.title = noticeBoardRequestDto.getTitle();
        this.contents = noticeBoardRequestDto.getContents();
        this.time = noticeBoardRequestDto.getTime();
    }
}
