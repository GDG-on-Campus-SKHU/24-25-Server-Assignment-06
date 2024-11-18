package com.example.sanghwajwt.jwt;

import com.example.sanghwajwt.user.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Iterator;

//로그인폼 disable할 경우 SpringSecurity 필터(UsernamePasswordAuthenticationFilter)를 사용못하기 때문에, 커스텀 LoginFilter생성
//LoginFilter는 UsernamePasswordAuthenticationFilter의 정의에 따라서 /login 경로로 오는 POST 요청을 검증하게 된다. controller로 넘어가기 전에 필터에서 처리하기 때문
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final Long expiredMs;

    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, Long expiredMs) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.expiredMs = expiredMs;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //클라이언트 요청에서 username, password 탈취
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        //탈취한 username과 password를 Token에 전달하고 토큰을 곧 매니저한테 전달할 것임
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        //최종적으로 authToken을 authenticate메서드가 검증할 것임 - 검증 방법 - DB에서 정보 꺼내오고 UserDetailsService에서 검증할거임
        return authenticationManager.authenticate(authToken);
    }

    //authenticationManager의 authenticate메서드가 검증에 성공할 경우 동작할 객체
    //이제 여기서 JWT를 발급하면됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        //인자로 받은 authentication을 customUserDetails에 담기
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        // username 추출
        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities != null && !authorities.isEmpty()) {
            Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
            if (iterator.hasNext()) {
                GrantedAuthority auth = iterator.next();
                String role = auth.getAuthority();
                // JWT 토큰 생성
                logger.info("expiredMs ::{}", expiredMs);
                String token = jwtUtil.createJwt(username, role, expiredMs);
                response.addHeader("Authorization", "Bearer " + token);
            } else {
                // 처리할 권한이 없을 때의 로직
                logger.warn("No authorities found for user: " + username);
            }
        } else {
            // 처리할 권한이 없거나 authorities가 null일 때의 로직
            logger.warn("Authorities collection is empty or null for user: " + username);
        }
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//        GrantedAuthority auth = iterator.next();
//        //role값 추출
//        String role = auth.getAuthority();
//        logger.info(username);
//
//        String token = jwtUtil.createJwt(username, "Role_" + role, expiredMss);
//        //HTTP인증 방식 RFC 7235정의 참고
//        response.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }
}

