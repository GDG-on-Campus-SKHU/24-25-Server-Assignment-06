package net.skhu.bomin.service;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.skhu.bomin.domain.Role;
import net.skhu.bomin.domain.User;
import net.skhu.bomin.dto.request.UserSignUpDto;
import net.skhu.bomin.dto.response.TokenDto;
import net.skhu.bomin.dto.response.UserInfoDto;
import net.skhu.bomin.jwt.TokenProvider;
import net.skhu.bomin.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.Principal;

@Service
@RequiredArgsConstructor(access= AccessLevel.PROTECTED)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public TokenDto signUp(UserSignUpDto userSignUpDto){
        User user = userRepository.save(User.builder()
                .email(userSignUpDto.getEmail())
                .pw(passwordEncoder.encode(userSignUpDto.getPw()))
                .phoneNumber(userSignUpDto.getPhoneNumber())
                .name(userSignUpDto.getName())
                .role(Role.USER) //역할 설정
                .build());

        String accessToken = tokenProvider.createAccessToken(user);

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();

    }


    //관리자
    @Transactional
    public TokenDto admin(UserSignUpDto userSignUpDto) {
        User user = userRepository.save(User.builder()
                .email(userSignUpDto.getEmail())
                .pw(passwordEncoder.encode(userSignUpDto.getPw()))
                .phoneNumber(userSignUpDto.getPhoneNumber())
                .name(userSignUpDto.getName())
                .role(Role.ADMIN) //역할 설정
                .build());

        String accessToken = tokenProvider.createAccessToken(user);

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    @Transactional(readOnly = true)
    public UserInfoDto findByPrincipal(Principal principal){
        Long userId = Long.parseLong(principal.getName());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return UserInfoDto.builder()
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .name(user.getName())
                .role(user.getRole().name())
                .build();
    }
}