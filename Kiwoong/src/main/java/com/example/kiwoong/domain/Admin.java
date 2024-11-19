package com.example.kiwoong.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ADMIN_EMAIL", nullable = false)
    private String email;

    @Column(name = "ADMIN_PASSWORD", nullable = false)
    private String password;

    @Column(name = "ADMIN_PHONE_NUMBER", nullable = false)
    private String phoneNumber;

    @Column(name = "AUTH_CODE")
    private String authCode;

    @Column(name = "ROLE")
    private String role;

    @Builder
    public Admin(String email, String password, String phoneNumber, String authCode, String role) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.authCode = authCode;
        this.role = role;
    }
}
