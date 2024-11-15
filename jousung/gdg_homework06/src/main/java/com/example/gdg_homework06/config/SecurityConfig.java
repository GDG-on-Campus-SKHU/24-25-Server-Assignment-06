package com.example.gdg_homework06.config;

import com.example.gdg_homework06.jwt.JwtFilter;
import com.example.gdg_homework06.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final TokenProvider tokenprovider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/gdg/**").permitAll()  // public API
                        .requestMatchers("/api/auth/**").permitAll()  // 인증 API
                        // 영화(관리자만 접근 가능)
                        .requestMatchers(HttpMethod.POST, "/api/movies").hasRole("ADMIN")  // 영화 생성
                        .requestMatchers(HttpMethod.PUT, "/api/movies/{id}").hasRole("ADMIN")   // 영화 수정
                        .requestMatchers(HttpMethod.DELETE, "/api/movies/{id}").hasRole("ADMIN") // 영화 삭제
                        .requestMatchers(HttpMethod.GET, "/api/movies/**").permitAll()  // 영화 조회

                        // 리뷰(유저만 접근 가능)
                        .requestMatchers(HttpMethod.POST, "/api/movies/{movieId}/reviews").hasRole("USER")  // 리뷰 생성
                        .requestMatchers(HttpMethod.PUT, "/api/movies/{movieId}/reviews/{reviewId}").hasRole("USER")  // 리뷰 수정
                        .requestMatchers(HttpMethod.DELETE, "/api/movies/{movieId}/reviews/{reviewId}").hasRole("USER") // 리뷰 삭제
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtFilter(tokenprovider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
