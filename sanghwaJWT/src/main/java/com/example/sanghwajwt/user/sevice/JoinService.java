package com.example.sanghwajwt.user.sevice;

import com.example.sanghwajwt.user.dto.JoinAdminDto;
import com.example.sanghwajwt.user.dto.JoinDto;
import com.example.sanghwajwt.user.entity.UserEntity;
import com.example.sanghwajwt.user.repository.UserRepository;
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
        System.out.println("Role설정 완료");
    }

    public void joinAdminProcess(JoinAdminDto joinAdminDto){
        Boolean isExist = userRepository.existsByUsername(joinAdminDto.getUsername());
        if(isExist) {
            return;
        }
        // adminKey를 따로 해시로 정의하는 로직 구현해야함
        if(joinAdminDto.getAdminKey() == 1234) {
            UserEntity userEntity = userRepository.save(UserEntity.builder()
                    .username(joinAdminDto.getUsername())
                    .password(passwordEncoder.encode(joinAdminDto.getPassword()))
                    .role("ROLE_ADMIN")
                    .build());
            System.out.println("Role설정 완료");
        }
        else{
            return;
        }
    }
}
