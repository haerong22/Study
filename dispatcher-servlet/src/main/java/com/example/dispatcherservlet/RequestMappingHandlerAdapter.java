package com.example.dispatcherservlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestMappingHandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return false;
    }

    @Override
    public Object handle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return null;
    }
}
