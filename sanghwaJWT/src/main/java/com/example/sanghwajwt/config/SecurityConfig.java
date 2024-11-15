package com.example.sanghwajwt.config;

import com.example.sanghwajwt.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //이 클래스는 Security를 위한 config이다.
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    //해시 암호화를 위한 엔코더 빈 등록
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                //JWT라 필요없음
                .httpBasic(AbstractHttpConfigurer::disable)
                //stateless상태이기때문에 csrf공격에 대응안해도 됨
                .csrf(AbstractHttpConfigurer::disable)
                //세션 설정
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //JWT라 필요없음
                //formLogin을 쓰지 않는 이유 : formLogin에 따라오는 로그인 페이지, 로그아웃 기능 등의 '세션' 부분 때문에
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                //인가 작업
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/login", "/", "/join").permitAll() //login, join url은 다 허용, 서비스 만들고 다시 확인
                        .requestMatchers("/admin").hasRole("ADMIN") //Admin 요청은 인가 설정
                        .anyRequest().authenticated()) //그 외 요청 전부 인증 받자
                //커스텀된 필터가 disable된 필터 자리에 추가
                //config에서도 authenticationManager를 추가해줘야한다.
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class)
                .build();

    }


}
