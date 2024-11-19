package com.example.jwtlogin.controller;

import com.example.jwtlogin.dto.request.BoardWriteDto;
import com.example.jwtlogin.dto.response.BoardInfoDto;
import com.example.jwtlogin.dto.response.UserInfoDto;
import com.example.jwtlogin.service.BoardService;
import com.example.jwtlogin.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;

    @PostMapping("/write")
    public ResponseEntity<BoardInfoDto> writeBoard(@RequestBody BoardWriteDto boardWriteDto, Principal principal) {
        UserInfoDto userInfoDto = userService.findByPrincipal(principal);
        BoardInfoDto boardInfoDto = boardService.write(boardWriteDto, userInfoDto);

        return ResponseEntity.ok(boardInfoDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardInfoDto> getBoardInfo(@PathVariable("id") Long id) {
        BoardInfoDto boardInfoDto = boardService.getBoardInfo(id);

        return ResponseEntity.ok(boardInfoDto);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BoardInfoDto> updateBoard(@RequestBody BoardWriteDto boardWriteDto, @PathVariable("id") Long id, Principal principal) {
        UserInfoDto userInfoDto = userService.findByPrincipal(principal);

        BoardInfoDto boardInfoDto = boardService.updateBoard(boardWriteDto, userInfoDto, id, userInfoDto.getUserId());

        return ResponseEntity.ok(boardInfoDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BoardInfoDto> deleteBoard(@PathVariable("id") Long id, Principal principal) {
        UserInfoDto userInfoDto = userService.findByPrincipal(principal);

        boardService.deleteBoard(id, userInfoDto.getUserId());

        return new ResponseEntity(HttpStatus.OK);
    }
}
