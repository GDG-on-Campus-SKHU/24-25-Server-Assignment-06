package com.example.jwtapplication.config;


import com.example.jwtapplication.jwt.JwtFilter;
import com.example.jwtapplication.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //어노테이션으로 해당 클래스를 설정 클래스로 인식하게 한다. 이늘 어플리케션 컨텍스트에 등록한다.
@RequiredArgsConstructor
public class SecurityConfig{

    private final TokenProvider tokenprovider;

    @Bean //Spring 컨텍스트에 SecurityFilerChain 빈을 등록한다.
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable) // Jwt를 사용해서 http의 기본이 되는 인증을 비활성화
                .csrf(AbstractHttpConfigurer::disable) // Restful API와 Stateless 인증를 사용하기에 CRSF 방어 불필요
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable) //Jwt를 사용해서 별도의 로그인 폼은 필요없음
                .logout(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/user","user/login","user/signUp").permitAll()
                        .requestMatchers("/restaurant/admin","/user/getUser").hasRole("ADMIN")
                        .requestMatchers("/reviews/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtFilter(tokenprovider), UsernamePasswordAuthenticationFilter.class) // 들어오는 요청에 대해 Jwt 토큰을 검증하고 인증 정보를 설정
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}