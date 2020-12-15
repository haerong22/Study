package com.security.jwt.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.jwt.auth.PrincipalDetails;
import com.security.jwt.model.User;
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
import java.util.Date;

// 스프링 시큐리티 에서 UsernamePasswordAuthenticationFilter는
// /login 요청해서 username, password를 전송하면 (post) 동작한다.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter : 로그인 시도중");

        try {
//            BufferedReader br = request.getReader();
//            String input = null;
//            while((input = br.readLine())!=null) {
//                System.out.println(input);
//
//            }
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println(user);
            UsernamePasswordAuthenticationToken  authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // PrincipalDetailsService 의 loadUserByUsername() 메소드 실행 후
            // 정상이면 authentication이 리턴됨.
            // DB에 있는 username과 password가 일치한다.
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            PrincipalDetails principalDetails= (PrincipalDetails) authentication.getPrincipal();
            System.out.println("로그인 완료 : " + principalDetails.getUser().getUsername()); // 값이 있다면 로그인 완료

            // authentication 객체를 return 하면 session영역에 저장됨. => 로그인 됨
            // JWT를 사용하면서 굳이 세션을 만들 필요가 없지만
            // 리턴의 이유는 권한 관리를 security가 대신 해주기 때문
            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("========================================");

        // 1. username, password 를 받는다.
        // 2. 정상인지 로그인 시도를 한다.
        // authenticationManager 로 로그인 시도를 하면
        // -> PrincipalDetailsService가 호출
        // -> loadUserByUsername() 메소드 실행
        // 3. PrincipalDetials 를 세션에 담고 (권한 관리를 위해서)
        // 4. JWT토큰을 만들어서 응답
        return null;
    }

    // attemptAuthentication 메소드 실행 후 인증이 정상적으로 되었으면
    // successfulAuthentication 메소드 실행
    // JWT 토큰을 만들어서 request요청한 사용자에게 JWT토큰을 response한다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                    FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("인증 완료 후 successfulAuthentication 실행");
        PrincipalDetails principalDetails= (PrincipalDetails) authResult.getPrincipal();

        // RSA방식이 아닌 HASH 방식 (secret key 필요 )
        String jwtToken = JWT.create()
                .withSubject("wj토큰")
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000 * 10)))
                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512("wj"));

        response.addHeader("Authorization", "Bearer " + jwtToken);
    }
}
