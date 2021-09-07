package com.example.dispatcherservlet;

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
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestURI = request.getRequestURI();

        Method[] declaredMethods = handler.getClass().getDeclaredMethods();

        Object result = "";
        for (Method declaredMethod : declaredMethods) {
            String value = declaredMethod.getAnnotation(RequestMapping.class).value();
            try {
                if (requestURI.equals(value)) {
                    result = declaredMethod.invoke(handler);
                    if (declaredMethod.isAnnotationPresent(ResponseBody.class)) {
                        response.setContentType("application/json");
                        response.getWriter().println(result);
                        return;
                    } else {
                        ViewResolver viewResolver = ApplicationStartUp.getInstance().getViewResolver((String) result);
                        viewResolver.render(request, response, (String) result);
                    }
                }
            } catch (IOException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
