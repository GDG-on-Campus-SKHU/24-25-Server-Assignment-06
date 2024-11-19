package com.example.seun.service;

import com.example.seun.entity.JwtBlacklist;
import com.example.seun.repository.JwtBlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtBlacklistService {

    private final JwtBlacklistRepository jwtBlacklistRepository;

    @Autowired
    public JwtBlacklistService(JwtBlacklistRepository jwtBlacklistRepository) {
        this.jwtBlacklistRepository = jwtBlacklistRepository;
    }

    public void blacklistToken(String token) {
        jwtBlacklistRepository.save(new JwtBlacklist(token));
    }

    public boolean isTokenBlacklisted(String token) {
        return jwtBlacklistRepository.existsByToken(token);
    }
}

