package com.example.jwtlogin.service;

import com.example.jwtlogin.repository.BoardRepository;
import com.example.jwtlogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void deleteUserByPrincipal(String userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public void deleteBoardById(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
