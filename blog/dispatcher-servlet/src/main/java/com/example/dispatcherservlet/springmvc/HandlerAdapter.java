package com.example.dispatcherservlet.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {

    boolean supports(Object handler);

    String handle(HttpServletRequest request, HttpServletResponse response, MappingRegistry handler);
}
