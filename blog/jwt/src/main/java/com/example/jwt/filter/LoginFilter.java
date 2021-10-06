package com.example.jwt.filter;

import com.example.jwt.utils.JwtUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String[] excludeUri = {
                "/login"
        };

        String requestURI = httpServletRequest.getRequestURI();

        if (Arrays.asList(excludeUri).contains(requestURI)) {
            chain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            try {
                String authorization = httpServletRequest.getHeader("Authorization");
                String token = authorization.replace("Bearer ", "");
                if (JwtUtils.verifyToken(token)) {
                    chain.doFilter(httpServletRequest, httpServletResponse);
                } else {
                    httpServletResponse.getWriter().println("Unauthorized");
                }
            } catch (Exception e) {
                httpServletResponse.getWriter().println("Unauthorized");
            }
        }
    }
}
