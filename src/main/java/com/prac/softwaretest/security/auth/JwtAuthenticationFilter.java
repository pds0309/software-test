package com.prac.softwaretest.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prac.softwaretest.dto.LoginRequest;
import com.prac.softwaretest.security.jwt.JwtConfig;
import com.prac.softwaretest.security.jwt.JwtSecretKey;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

// 커스텀 인증 필터
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final JwtSecretKey secretKey;
    private final ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter");

        // 클라이언트 로그인 요청 json 파싱해서 DTO 로 받음
        LoginRequest loginRequest = null;
        try {
            loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 인증 객체 생성
        Authentication authentication = toAuthentication(loginRequest);

        // authenticate() 함수가 호출 되면 인증 프로바이더가 UserDetailsService 의
        // loadUserByUsername을 호출하고
        // UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
        // UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
        // Authentication 객체를 만들어서 필터체인으로 리턴해준다.
        return authenticationManager.authenticate(authentication);
    }

    // 인증에 성공하면 JWT 토큰을 생성해 Response 에 담아준다
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        MemberDetails memberDetails = (MemberDetails) authResult.getPrincipal();
        String token = Jwts.builder()
                .setSubject(memberDetails.getMember().getName())
                .claim("authorities", memberDetails.getAuthorities())
                .claim("id", memberDetails.getMember().getId())
                .claim("name", memberDetails.getMember().getName())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                .signWith(secretKey.secretKey())
                .compact();
        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
    }

    private Authentication toAuthentication(LoginRequest loginRequest) {
        // return Principal, Credential
        return new UsernamePasswordAuthenticationToken(loginRequest.getName(),loginRequest.getPassword());
    }
}
