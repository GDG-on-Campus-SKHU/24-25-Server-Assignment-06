package com.example.sanghwajwt.service;

import com.example.sanghwajwt.dto.JoinDto;
import com.example.sanghwajwt.entity.UserEntity;
import com.example.sanghwajwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    //repondEntity 해야됨
    public void joinProcess(JoinDto joinDto){
        Boolean isExist = userRepository.existsByUsername(joinDto.getUsername());
        if(isExist){
            return;
        }
        //Role은 아직
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .username(joinDto.getUsername())
                .password(passwordEncoder.encode(joinDto.getPassword()))
                .role("ROLE_USER")
                .build());

    }
}
