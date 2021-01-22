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

        String exception = request.getRequestURI();

        if (exception.equals("/jspblog/") || exception.equals("/jspblog/index.jsp") || exception.equals("/jspblog/user/jusoPopup.jsp")) {
            chain.doFilter(request, response);
        } else {
            PrintWriter out = response.getWriter();
            out.print("잘못된 접근입니다.");
            out.flush();
        }
    }
}
