package com.example.jwt.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwt.config.auth.PrincipalDetails;
import com.example.jwt.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Date;
// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음
// /login 요청해서 username, password 전송하면
//  UsernamePasswordAuthenticationFilter 동작함
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter:로그인 시도중");
        // 1. username, password 받아서
        try {
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            log.info("user={}", user);

            // Token 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            log.info("authenticationToken={}", authenticationToken);

            // PrincipalDetailsService 의 loadUserByUsername 함수가 실행된 후 정상이면 authentication 이 리턴
            // DB에 있는 username과 password가 일치
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            log.info("authentication={}", authentication);

            //  로그인이 되었다는 뜻
            PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
            log.info("principalDetails.getUser().getUsername()={}", principalDetails.getUser().getUsername());
            System.out.println("로그인 완료됨: "+principalDetails.getUser().getUsername()); // 로그인 정상적으로 되었음
            // authentication 객체가 session 영역에 저장해야하고 그 방법이 return
            // return의 이유는 권한 관리를 security가 대신 해주기 떄문에 편하려고 사용함
            // 굳이 JWT 토큰을 사용하면서 세션을 만들이유가 없음 단지 권한 처리떄문에 session에 넣어줌
            return authentication;

        } catch (IOException e) {
            e.printStackTrace();
        }
        // 2. 정상인지 로그인 시도 authenticationManager 로 로그인 시도를 하면
        // PrincipalDetailsService 가 호출 loadUserByUsername 실행
        // 3. PrincipalDetails 를 세션에 담고(권한 관리를 위해서)
        // 4. JWT 토큰을 만들어서 응답
        return null;
    }

    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행
    // JWT토큰을 만들어서 request요청을 한 사용자에게 JWT 토큰을 response해주면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨: 인증완료");
        PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();
        log.info("principalDetails={}", principalDetails);
        // RSA방식이 아니라 Hash암호방식
        String jwtToken = JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME)) // 만료 시간
                .withClaim("userId", principalDetails.getUser().getUserId())
                .withClaim("username",principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        log.info("jwtToken={}", jwtToken);
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

    }
}
