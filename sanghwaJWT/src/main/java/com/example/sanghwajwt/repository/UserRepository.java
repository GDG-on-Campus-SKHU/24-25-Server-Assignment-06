package com.example.sanghwajwt.repository;

import com.example.sanghwajwt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    //JPA구문
    Boolean existsByUsername(String username);
}
