package com.example.jwtlogin.controller;

import com.example.jwtlogin.dto.response.UserInfoDto;
import com.example.jwtlogin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService;

    @DeleteMapping("/delUser/{id}")
    public ResponseEntity<UserInfoDto> delUser(@PathVariable String id) {
        adminService.deleteUserByPrincipal(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delBoard/{id}")
    public ResponseEntity<UserInfoDto> delBoard(@PathVariable Long id) {
        adminService.deleteBoardById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
