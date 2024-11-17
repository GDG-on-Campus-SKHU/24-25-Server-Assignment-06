package com.example.inseok.User.domain;

import com.example.inseok.User.dto.request.UserRequestDto;
import com.example.inseok.noticeBoard.domain.NoticeBoard;
import com.example.inseok.noticeBoard.dto.request.NoticeBoardRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_PASSWORD", nullable = false)
    private String password;

    @Column(name = "USER_NICKNAME", nullable = false)
    private String nickName;

    @Column(name = "USER_EMAIL", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_ROLE", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoticeBoard> noticeBoards = new ArrayList<>();

    @Builder
    public User(String password, String nickName,String email, Role role) {
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.role = role;
    }

    public void update(UserRequestDto userRequestDto) {
        this.password = userRequestDto.getPassword();
        this.nickName = userRequestDto.getNickName();
        this.email = userRequestDto.getEmail();
    }
}
