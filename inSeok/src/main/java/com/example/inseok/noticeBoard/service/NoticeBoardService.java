package com.example.inseok.noticeBoard.service;

import com.example.inseok.User.domain.User;
import com.example.inseok.User.dto.request.UserRequestDto;
import com.example.inseok.User.repository.UserRepository;
import com.example.inseok.noticeBoard.domain.NoticeBoard;
import com.example.inseok.noticeBoard.dto.request.NoticeBoardRequestDto;
import com.example.inseok.noticeBoard.dto.response.NoticeBoardInfoDto;
import com.example.inseok.noticeBoard.repository.NoticeBoardRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeBoardService {
    private final NoticeBoardRepository noticeBoardRepository;
    private final UserRepository userRepository;

    @Transactional
    public NoticeBoardInfoDto saveNoticeBoard(Principal principal, NoticeBoardRequestDto noticeBoardRequestDto) {
        Long userId = Long.parseLong(principal.getName());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        NoticeBoard saveNoticeBoard = noticeBoardRepository.save(NoticeBoard.builder()
                .user(user)
                .title(noticeBoardRequestDto.getTitle())
                .contents(noticeBoardRequestDto.getContents())
                .time(noticeBoardRequestDto.getTime())
                .build());

        return NoticeBoardInfoDto.from(saveNoticeBoard);

    }
    @Transactional(readOnly = true)
    public NoticeBoardInfoDto getNoticeBoard(Long noticeBoardId) {
       NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId)
               .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        return NoticeBoardInfoDto.from(noticeBoard);
    }
    @Transactional
    public NoticeBoardInfoDto updateNoticeBoard(Principal principal,Long noticeBoardId, NoticeBoardRequestDto noticeBoardRequestDto) {
        Long userId = Long.parseLong(principal.getName());
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        if (noticeBoard.getUser().getId() != userId) {
            throw new RuntimeException("본인의 게시물만 수정 가능합니다.");
        }

        noticeBoard.update(noticeBoardRequestDto);
        return NoticeBoardInfoDto.from(noticeBoard);

    }
    @Transactional
    public void deleteNoticeBoard(Principal principal, Long noticeBoardId) {
        Long userId = Long.parseLong(principal.getName());
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        if(noticeBoard.getUser().getId() != userId) {
            throw new RuntimeException("본인의 게시물만 삭제 가능합니다.");
        }
        noticeBoardRepository.delete(noticeBoard);
    }
}
