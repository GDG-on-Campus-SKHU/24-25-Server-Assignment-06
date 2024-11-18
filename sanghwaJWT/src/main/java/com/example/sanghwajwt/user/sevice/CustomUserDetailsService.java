package com.example.sanghwajwt.user.sevice;

import com.example.sanghwajwt.user.dto.CustomUserDetails;
import com.example.sanghwajwt.user.entity.UserEntity;
import com.example.sanghwajwt.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//AuthenticationManager는 인터페이스로 등록된 UserDetailsService의 구현부를 찾고 내부 loadByUsername메소드를 실행한다.
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;

    //데이터베이스에서 가져온 UserEntity가 널이면
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);

//        logger.info("username :: {}", username);

        if (user != null) {
            logger.info("user 전달완료 ::{}", user);
            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new CustomUserDetails(user);
        }
        //fix
        //UserDetailsService returned null, which is an interface contract violation
        return null;
    }
}
