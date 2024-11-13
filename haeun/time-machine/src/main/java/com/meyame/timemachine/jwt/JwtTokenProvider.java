package com.meyame.timemachine.jwt;

import com.meyame.timemachine.domain.auth.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

// jwt 토큰 생성 및 검증
@Component // 빈으로 등록하여 다른 곳에서 의존성 주입 받아 자유롭게 설정할 수 있도록 한다.
public class JwtTokenProvider {

    private static final String ROLE_CLAIM = "Role"; // JWT 의 payload 부분에 있다. (그니까 클이 서버에 요청할 때 토큰을 Authorization 헤더에 포함하여 전송함)
    private static final String BEARER = "Bearer "; // 토큰을 헤더에 포함할 때 사용되는 문자열. Bearer 토큰. 이런식으로 저장된다.
    private static final String AUTHORIZATION = "Authorization";

    private final Key key; // jwt 서명을 위해 사용할 암호화 키
    private final long accessTokenValidityTime;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
                         @Value("${jwt.access-token-validity-in-milliseconds}") long accessTokenValidityTime) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenValidityTime = accessTokenValidityTime;
    }

    public String createAccessToken(User user) {
        long nowTime = (new Date().getTime());

        Date accessTokenExpiredTime = new Date(nowTime + accessTokenValidityTime);

        return Jwts.builder()
                // JWT 표준에 미리 정의된 필드들 - Registered Claims -> set__ 함수로 정의
                .setSubject(user.getId().toString())
                // 사용자 정의 Claims (Custom Claims) - 표준에 정의되지 않은 추가 정보
                // -> claim() 함수로 정의 (Role 이나 Permission 같이)
                .claim(ROLE_CLAIM, user.getRole().name())
                .setExpiration(accessTokenExpiredTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰에서 사용자 정보를 가져온다.
    public Authentication getAuthentication(String accessToken) {
        // accessToken 을 분석하여 Payload 부분에 있는 Claims 를 가져온다.
        Claims claims = parseClaims(accessToken);

        // 해당 Claims 에는 역할 정보도 포함되어 있는데, null 경우 예외 처리
        if (claims.get(ROLE_CLAIM) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 사용자의 역할 정보를 securityContextHolder에 담아준다
        /*
            ex> ROLE_CLAIM 이 "USER,ADMIN" 값을 갖고 있다면,
            최종적으로 ["ROLE_USER","ROLE_ADMIN"] 이런식으로 바꿔준다.
         */
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(ROLE_CLAIM).toString().split(","))
                        // 해당 hasRole이 권한 정보를 식별하기 위한 전처리 작업
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList());

        // authorities 권한 리스트가 Authentication 객체에 설정된다.
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);
        authentication.setDetails(claims);

        return authentication;
    }

    // 토큰 추출
    public String resolveToken(HttpServletRequest request) { //토큰 분해/분석
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (UnsupportedJwtException | ExpiredJwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // JWT 토큰을 파싱하여 Payload 부분에 있는 Claims 객체 추출
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key) // 토큰이 생성될 때 사용된 서명 키와 동일해야한다.
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) { // 토큰의 유효기간이 만료된 경우
            return e.getClaims(); // 만료되니 경우에도 만료된 Claims 반환
        } catch (SignatureException e) { // 토큰의 서명이 올바르지 않은 경우
            throw new RuntimeException("토큰 복호화에 실패했습니다.");
        }
    }
}
