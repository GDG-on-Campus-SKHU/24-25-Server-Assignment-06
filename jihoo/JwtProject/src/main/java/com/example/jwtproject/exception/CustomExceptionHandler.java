package com.example.jwtproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        // 403 상태 코드와 메시지를 함께 반환
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("접근이 거부되었습니다. 관리자 권한이 필요합니다.");
    }
}
