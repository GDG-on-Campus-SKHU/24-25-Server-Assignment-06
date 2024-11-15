package com.example.sanghwajwt.service;

import com.example.sanghwajwt.dto.CustomUserDetails;
import com.example.sanghwajwt.entity.UserEntity;
import com.example.sanghwajwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);

        if (user != null) {
            return new CustomUserDetails(user);
        }
        return null;
    }
}
