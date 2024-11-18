package com.example.sanghwajwt.user.repository;

import com.example.sanghwajwt.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    //JPA구문
    Boolean existsByUsername(String username);

    UserEntity findByUsername(String username);
}
