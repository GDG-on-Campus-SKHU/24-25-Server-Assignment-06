package com.example.jwtlogin.service;

import com.example.jwtlogin.domain.Board;
import com.example.jwtlogin.domain.User;
import com.example.jwtlogin.dto.request.BoardWriteDto;
import com.example.jwtlogin.dto.response.BoardInfoDto;
import com.example.jwtlogin.dto.response.UserInfoDto;
import com.example.jwtlogin.repository.BoardRepository;
import com.example.jwtlogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public BoardInfoDto write(BoardWriteDto boardWriteDto, UserInfoDto userInfoDto) {
        User writer = User.builder()
                .userId(userInfoDto.getUserId())
                .userName(userInfoDto.getUsername())
                .password(userInfoDto.getPassword())
                .role(userInfoDto.getRole())
                .build();

        Board board = Board.builder()
                .title(boardWriteDto.getTitle())
                .content(boardWriteDto.getContent())
                .writer(writer)
                .build();

        boardRepository.save(board);

        return BoardInfoDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writerName(board.getWriter().getUserName())
                .build();
    }

    @Transactional
    public BoardInfoDto getBoardInfo(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        return BoardInfoDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writerName(board.getWriter().getUserName())
                .build();
    }

    @Transactional
    public BoardInfoDto updateBoard(BoardWriteDto boardWriteDto, UserInfoDto userInfoDto, Long boardId, String userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        if(!Objects.equals(userId, board.getWriter().getUserId()))
            throw new IllegalArgumentException("수정할 권한이 없습니다.");

        return BoardInfoDto.builder()
                .id(board.getId())
                .title(boardWriteDto.getTitle())
                .content(boardWriteDto.getContent())
                .writerName(board.getWriter().getUserName())
                .build();
    }

    @Transactional
    public void deleteBoard(Long boardId, String userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        if(!Objects.equals(userId, board.getWriter().getUserId()))
            throw new IllegalArgumentException("삭제할 권한이 없습니다.");

        boardRepository.deleteById(boardId);
    }
}
