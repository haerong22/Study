package com.security.jwt.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter3 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        // id, pw 정상적으로 들어와서 로그인이 완료 되면 토큰을 만들어주고 응답
        // 요청할 때 마다 header에 Authorization에 value 값으로 토큰이 넘어온다.
        // 넘어온 토큰이 내가 만든 토큰이 맞는지 검증만 하면 된다. ( RSA, HS256 )
        if(req.getMethod().equals("POST")) {
            System.out.println("POST 요청됨");
            String headerAuth = req.getHeader("Authorization");
            System.out.println(headerAuth);

            if (headerAuth != null && headerAuth.equals("cos")) {
                filterChain.doFilter(req, res);
            } else {
                PrintWriter out = res.getWriter();
                out.println("인증 안됨");
            }
        } else {
            filterChain.doFilter(req, res);
        }
    }
}
