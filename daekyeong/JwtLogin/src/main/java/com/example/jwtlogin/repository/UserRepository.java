package com.example.jwtlogin.repository;

import com.example.jwtlogin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
