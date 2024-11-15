package com.example.kiwoong.repository;


import com.example.kiwoong.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
