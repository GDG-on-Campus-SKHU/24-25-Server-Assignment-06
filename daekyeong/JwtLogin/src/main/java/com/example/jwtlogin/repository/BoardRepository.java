package com.example.jwtlogin.repository;

import com.example.jwtlogin.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
