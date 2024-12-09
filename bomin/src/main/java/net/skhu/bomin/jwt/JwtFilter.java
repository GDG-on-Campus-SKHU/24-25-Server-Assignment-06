package net.skhu.bomin.jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import java.io.IOException;

@RequiredArgsConstructor //final로 선언된 필드에 대한 생성자 자동으로 생성
//HTTP요청을 처리하는 필터
public class JwtFilter extends GenericFilterBean {
    private final TokenProvider TR; //jwt 생성, 검증, 인증 정보 제공

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException{ //HTTP요청과 응답을 처리
        String token = TR.resolveToken((HttpServletRequest) request); //HTTP요청에서 JWT추출.

        if(StringUtils.hasText(token) && TR.validateToken(token)){ //토큰 비어있지않고 유효한지 확인 && 토큰 유효한지 검증
            Authentication authentication = TR.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //가져온 인증 정보를 SecurityContext에 설정하여 현재 스레드의 보안 컨텍스트에 인증 정보를 저장. 이후에 요청 처리에서 인증된 사용자 정보를 사용할 수 있다.
        }
        filterChain.doFilter(request, response); //요청과 응답 전달. 이 호출이 없으면 요청 처리 중단됨.
    }
}
