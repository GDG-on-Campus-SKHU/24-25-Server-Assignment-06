package com.example.inseok.noticeBoard.controller;

import com.example.inseok.User.dto.request.UserRequestDto;
import com.example.inseok.noticeBoard.domain.NoticeBoard;
import com.example.inseok.noticeBoard.dto.request.NoticeBoardRequestDto;
import com.example.inseok.noticeBoard.dto.response.NoticeBoardInfoDto;
import com.example.inseok.noticeBoard.service.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/noticeBoard")
@RequiredArgsConstructor
public class NoticeBoardController {
    private final NoticeBoardService noticeBoardService;

    @PostMapping
    public ResponseEntity<NoticeBoardInfoDto> saveNoticeboard(Principal principal, @RequestBody NoticeBoardRequestDto noticeBoardRequestDto) {
        noticeBoardService.saveNoticeBoard(principal, noticeBoardRequestDto);

        return ResponseEntity.ok().body(noticeBoardService.saveNoticeBoard(principal, noticeBoardRequestDto));
    }

    @GetMapping("/{noticeBoardId}")
    public ResponseEntity<NoticeBoardInfoDto> getNoticeBoard(Principal principal, @PathVariable Long noticeBoardId) {
        return ResponseEntity.ok().body(noticeBoardService.getNoticeBoard(noticeBoardId));
    }

    /*GetMapping 모든 게시물 조회 구현 x */

    @PatchMapping("/{noticeBoardId}")
    public ResponseEntity<NoticeBoardInfoDto> updateNoticeBoard(Principal principal, @PathVariable Long noticeBoardId, @RequestBody NoticeBoardRequestDto noticeBoardRequestDto) {
        return ResponseEntity.ok().body(noticeBoardService.updateNoticeBoard(principal, noticeBoardId, noticeBoardRequestDto));
    }

    @DeleteMapping("/{noticeBoardId}")
    public ResponseEntity<Void> deleteNoticeBoard(Principal principal, @PathVariable Long noticeBoardId) {
        noticeBoardService.deleteNoticeBoard(principal, noticeBoardId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
