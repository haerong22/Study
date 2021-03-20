package com.example.restcontroller.common.interceptor;

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
        log.info(request.getMethod());
        log.info(request.getRequestURI());

        return true;
    }
}
