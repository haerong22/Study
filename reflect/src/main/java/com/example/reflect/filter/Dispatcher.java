package com.example.reflect.filter;

import com.example.reflect.controller.UserController;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Dispatcher implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

//        System.out.println("컨텍스트 : " + request.getContextPath());
//        System.out.println("식별자주소 : " + request.getRequestURI());
//        System.out.println("전체주소 : " + request.getRequestURL());

        String endPoint = request.getRequestURI().replaceAll(request.getContextPath(), "");

        UserController userController = new UserController();
        if(endPoint.equals("/join")){
            userController.join();
        }else if(endPoint.equals("/login")){
            userController.login();
        }
    }
}
