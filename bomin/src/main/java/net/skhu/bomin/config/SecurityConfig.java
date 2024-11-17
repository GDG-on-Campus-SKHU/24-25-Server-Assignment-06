package net.skhu.bomin.config;

import lombok.RequiredArgsConstructor;
import net.skhu.bomin.jwt.JwtFilter;
import net.skhu.bomin.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //빈 정의하는 설정 클래스. 정의된 메서드에서 반환하는 객체들을 Spring 빈으로 등록
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;

    @Bean//Spring 컨텍스트에 SecurityFilterChain 빈 등록
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity //보안설정
                .httpBasic(AbstractHttpConfigurer::disable) //기본 인증 비활성화.
                .csrf(AbstractHttpConfigurer::disable) //csrf 보호 비활성화
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS)) // 서버가 세션을 생성하지 않도록 설정 -> 클라이언트의 상태를 서버가 유지 X
                .formLogin(AbstractHttpConfigurer::disable) //폼 로그인 비활성화
                .logout(AbstractHttpConfigurer::disable) //로그아웃 기능 비활성화
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/gdg/**").permitAll() //gdg경로에 대한 접근은 인증 없이 허용한다.
                        .anyRequest().authenticated() //그 외의 경로는 인증된 사용자만 접근 가능 -> 즉, 사용자는 로그인해야만 접근 가능
                )
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class) // 사용자 이름과 비밀번호로 인증하기 전에 JWT 필터 실행된다
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ //Spring Security에서 pw 안전하게 인코딩하기 위한 PWEncoder 빈 생성 코드
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); //BCrypt 알고리즘을 사용하여 인코딩된다.
    }
}
