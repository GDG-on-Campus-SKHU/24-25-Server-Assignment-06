package com.example.inseok.noticeBoard.repository;

import com.example.inseok.noticeBoard.domain.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {
}
