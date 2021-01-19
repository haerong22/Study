package com.example.jspblog.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CharConfig implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

//        String username = request.getParameter("username");
//        System.out.println("username : " + username);
//
//        PrintWriter out = response.getWriter();
//        out.println("안녕");
//        out.flush();

        filterChain.doFilter(request, response);
    }
}
