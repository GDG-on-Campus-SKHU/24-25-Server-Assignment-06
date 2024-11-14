package com.example.sanghwajwt.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//로그인폼 disable할 경우 SpringSecurity 필터를 사용못하기 때문에, 커스텀 LoginFilter생성
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    //UPA에서 필요로 하는 메서드, 왜 필요한지 서치
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //클라이언트 요청에서 username, password 탈취
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        //탈취한 username과 password를 Token에 전달하고 토큰을 곧 매니저한테 전달할 것임
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        //최종적으로 authToken을 authenticate메서드가 검증할 것임 - 검증 방법 - DB에서 정보 꺼내오고 UserDetailsService에서 검증할거임
        return authenticationManager.authenticate(authToken);
    }

    //authenticationManager의 authenticate메서드가 검증에 성공할 경우 만들어질 객체
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication){
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed){
    }
}

