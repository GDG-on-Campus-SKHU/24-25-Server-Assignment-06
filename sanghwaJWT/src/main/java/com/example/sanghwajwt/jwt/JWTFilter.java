package com.example.sanghwajwt.jwt;

import com.example.sanghwajwt.dto.CustomUserDetails;
import com.example.sanghwajwt.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Jwt 검증할 필터
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private UserEntity userEntity;
    //메서드 자동완성
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //request에서 헤더의 authorize키 값을 뽑아온다.
        String authorization = request.getHeader("Authorization");
        System.out.println("authorizaion:" + authorization);

        //authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token null");
            //return하기전에 dofilter라는 다음 필터로 넘겨준다.
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];

        //토큰 소멸시간 검증
        if (jwtUtil.isExpired(token)){
            System.out.println("token expired");
            filterChain.doFilter(request, response);

            return;
        }

        //token이 null이 아니고, 소멸시간도 검증이 되었다면, 일시적으로 세션을 만든다.
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        userEntity = UserEntity.builder()
                .username(jwtUtil.getUsername(token))
                .role(jwtUtil.getRole(token))
                .password("temppassword")
                .build();

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        //그 다음 필터에 넘겨줌
        filterChain.doFilter(request, response);

    }


}
