package com.example.sanghwajwt.jwt;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {
    private final SecretKey secretKey;
    private final long accessTokenValidityTime;


    //JWTUtil이 호출될 때, 파라미터로 yml에 설정해놓은 값들을 불러옴
    public JWTUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.access-token-validity-in-milliseconds}") long accessTokenValidityTime) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.accessTokenValidityTime = accessTokenValidityTime;
    }

    public String getUsername(String token) {
        //토큰의 secretKey가 우리 서버에서 만든 것인지 확인, Claims확인,  Payload에서 String 타입의 username키 값을 가져온다.
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getRole(String token) {
        //토큰의 secretKey가 우리 서버에서 만든 것인지 확인
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {
        //토큰의 secretKey가 우리 서버에서 만든 것인지 확인
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String createJwt(String username, String role, long expiredMs) {
        return Jwts.builder()
                //토큰을 만들때 claim메서드로 username과 role을 넣어줌
                .claim("username", username)
                .claim("role", role)
                //현재 발행시간
                .issuedAt(new Date(System.currentTimeMillis()))
                //현재 발행시간 + 인자로 받을 시간으로 만료 시간을 설정
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                //signWith으로 secretKey를 활용하여 암호화 후 시그니처 생성
                .signWith(secretKey)
                .compact();
    }
}
