package com.security.jwt.filter;

import javax.servlet.*;
import java.io.IOException;

public class MyFilter1 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        System.out.println("필터1");

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
