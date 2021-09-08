package com.example.dispatcherservlet;

import com.example.dispatcherservlet.annotation.Controller;
import com.example.dispatcherservlet.annotation.RequestMapping;
import com.example.dispatcherservlet.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestMappingHandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler.getClass().isAnnotationPresent(Controller.class);
    }

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestURI = request.getRequestURI();

        Method[] declaredMethods = handler.getClass().getDeclaredMethods();

        Object result = "";
        for (Method declaredMethod : declaredMethods) {
            String value = declaredMethod.getAnnotation(RequestMapping.class).value();
            try {
                if (requestURI.equals(value)) {
                    result = declaredMethod.invoke(handler);
                    if (declaredMethod.isAnnotationPresent(ResponseBody.class)) {
                        if (result instanceof String) {
                            response.setContentType("text/plain");
                            response.getWriter().println(result);
                        }
                        return null;
                    }
                }
            } catch (InvocationTargetException | IllegalAccessException | IOException e) {
                e.printStackTrace();
            }
        }
        return (String) result;
    }
}
