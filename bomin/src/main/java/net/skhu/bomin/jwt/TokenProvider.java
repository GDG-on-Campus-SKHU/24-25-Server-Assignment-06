package net.skhu.bomin.jwt;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import net.skhu.bomin.domain.User;
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

@Component
public class TokenProvider {
    private static final String ROLE_CLAIM = "ROLE"; //사용자 역할 정보 저장 클레임
    private static final String BEARER ="Bearer "; //Jwt토큰 형식 나태내는 문자열
    private static final String AUTHORIZATION = "Authorization"; //HTTP 요청 헤더에서 JWT 토큰을 찾기 위해 사용하는 헤더 이름

    private final Key key; //JWT 서명을 위한 비밀 키
    private final long accessTokenValidityTime; //생성된 액세스 토큰의 유효 기간을 밀리초 단위로 저장

    //@Value 어노테이션을 사용하여 application.yml 파일에서 비밀키와 액세스 토큰 유효 기간 주입 받기
    public TokenProvider(@Value("${jwt.secret}") String secretKey,// 인코딩된 key
                         @Value("${jwt.access-token-validity-in-milliseconds}") long accessTokenValidityTime){  //토큰 유효시간
        byte[] KeyBytes = Decoders.BASE64.decode(secretKey); //BASE64로 key 디코딩. JWT의 서명 및 검증을 위해서는 비밀키가 바이트 배열 형태여야 한다.
        this.key = Keys.hmacShaKeyFor(KeyBytes); //HMAC SHA키 생성. 이 알고리즘은 비밀 키를 사용하여 메세지 무결성 보장
        this.accessTokenValidityTime = accessTokenValidityTime;
    }

    public String createAccessToken(User user){
        long nowTime = (new Date().getTime());
        Date accessTokenExpiredTime = new Date(nowTime + accessTokenValidityTime); //현재시간이랑 유효시간 더해서 토큰 만료 시간 설정
        return Jwts.builder() //jwt 생성
                .setSubject(user.getId().toString()) //토큰을 통해 인증된 사용자 식별
                .claim(ROLE_CLAIM, user.getRole().name()) //추가 정보를 jwt에 포함시키기
                .setExpiration(accessTokenExpiredTime)  //jwt 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256) //jwt 서명하는데 사용. key=비밀키, HMAC SHA-256알고리즘 사용
                .compact(); //모든 설정을 바탕으로 jwt를 생성하고, 최종적으로 문자열 형식으로 반환
    }

    //JWT 유효성 검사하고 필요한 경우 클레임 반환
    private Claims parseClaims(String accessToken){
        try{
            return Jwts.parserBuilder()//주어진 데이터를 분석하고 구조화된 형식으로 변환하는 과정(파싱)하기 위한 빌더
                    .setSigningKey(key)//서명 검증하기 위해 사용되는 키 설정
                    .build()//JWT파서 객체 생성
                    .parseClaimsJws(accessToken) //accessToken 파싱하여 jwt의 클레임을 포함하는 Jws<Claims>객체 반환. 이 메서드는 jwt의 서명을 검증하고, 유효한 경우 클레임을 포함한 객체 반환
                    .getBody();//Jws<Claims> 객체에서 클레임 본문을 추출하여 반환
        }catch(ExpiredJwtException e){ //jwt 만료된 경우
            return e.getClaims();//jwt 클레임 반환. -> 만료된 토큰이더라도 클레임 정보를 사용할 수 있도록 하기 위해
        }catch (SignatureException s){ //서명이 유효하지 않을 경우
            throw new RuntimeException("토큰 복호화에 실패했습니다.");
        }
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        if (claims.get(ROLE_CLAIM) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        // 사용자의 권한 정보를 securityContextHolder에 담아준다
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(ROLE_CLAIM).toString().split(","))
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList());

        //JWT의 주체(subject)정보를 가져오기. 사용자의 이메일이나 ID가 해당된다. /pw 가리킴. 근데 jwt기반 인증은 pw필요없으니 빈 문자열 / 앞서 추출한 권한 정보 설정
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);
        authentication.setDetails(claims); //객체에 추가적인 세부 정보 설정.

        return authentication;
    }

    //토큰 분해/분석
    public String resolveToken(HttpServletRequest request){ // HTTP 요청에서 Bearer 토큰을 추출하는 메서드
        String bearerToken = request.getHeader(AUTHORIZATION);//AUTHORIZATION이라는 이름의 헤더 값을 가져온다.

        //bearerToken이 null이 아닌 공백이 아닌지 확인 && bearerToken이 "Bearer"로 시작하는지 확인
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)){
            return bearerToken.substring(7); // bearerToken의 7번째 인덱스부터 시작하여 문자열의 끝까지의 부분 문자열을 반환
        }
        return null; //위의 내용 만족 안할 시 Bearer토큰 추출X
    }

    //jwt 유효한지 검증하는 기능 수행(토큰 만료, 형식 올바른지)
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true; //토큰 유효? true
        }catch(UnsupportedJwtException | ExpiredJwtException | IllegalArgumentException e ){
            return false;
        }
    }
}