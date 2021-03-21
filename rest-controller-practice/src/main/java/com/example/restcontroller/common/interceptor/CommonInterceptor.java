package com.example.restcontroller.common.interceptor;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.example.restcontroller.common.exception.AuthFailException;
import com.example.restcontroller.common.model.ResponseResult;
import com.example.restcontroller.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CommonInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("#########################################");
        log.info("[interceptor] - preHandler start");
        log.info("#########################################");
        log.info(request.getMethod() + " " + request.getRequestURI());

        if (!request.getRequestURI().equals("/api/login") && !validJWT(request)) {
            throw new AuthFailException("인증정보가 정확하지 않습니다");
        }
        return true;
    }

    private boolean validJWT(HttpServletRequest request) {

        String token = request.getHeader("TOKEN");

        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        request.setAttribute("email", email);

        return true;
    }
}
