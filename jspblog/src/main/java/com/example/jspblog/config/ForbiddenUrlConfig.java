package com.example.jspblog.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ForbiddenUrlConfig implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if (request.getRequestURI().equals("/jspblog/") || request.getRequestURI().equals("/jspblog/index.jsp")) {
            chain.doFilter(request, response);
        } else {
            PrintWriter out = response.getWriter();
            out.print("잘못된 접근입니다.");
            out.flush();
        }
    }
}
