package com.example.tripplanner.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_PASSWORD", nullable = false)
    private String password;

    @Column(name = "USER_EMAIL", nullable = false)
    private String email;

    @Column(name = "USER_PHONE", nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trip> trips = new ArrayList<>();

    @Builder
    public User(String password, String email, String phoneNumber, Role role) {
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = (role != null) ? role : Role.ROLE_USER;
    }

}
