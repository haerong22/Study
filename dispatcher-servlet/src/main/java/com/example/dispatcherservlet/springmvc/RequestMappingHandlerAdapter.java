package com.example.dispatcherservlet.springmvc;

import com.example.dispatcherservlet.annotation.Controller;
import com.example.dispatcherservlet.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestMappingHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler.getClass().isAnnotationPresent(Controller.class);
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response, MappingRegistry handler) {
        Object result = null;
        try {
            Method method = handler.getMethod();
            result = method.invoke(handler.getHandler());
            if (method.isAnnotationPresent(ResponseBody.class)) {
                if (result instanceof String) {
                    response.setContentType("text/plain");
                    response.getWriter().println(result);
                }
                return null;
            }
        } catch (InvocationTargetException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
        return (String) result;
    }
}
