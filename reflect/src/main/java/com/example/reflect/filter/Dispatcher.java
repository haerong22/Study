package com.example.reflect.filter;

import com.example.reflect.anno.RequestMapping;
import com.example.reflect.controller.UserController;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        System.out.println("엔드포인트 : " + endPoint);
        UserController userController = new UserController();
//        if(endPoint.equals("/join")){
//            userController.join();
//        }else if(endPoint.equals("/login")){
//            userController.login();
//        }
//
//        Method[] methods = userController.getClass().getMethods(); // 모든 메소드만( 상속 된 메소드 포함)
        // 리플렉션 -> 메소드를 런타임 시점에서 찾아내서 실행
        Method[] methods = userController.getClass().getDeclaredMethods(); // 그 파일의 메소드만
//        for(Method method : methods) {
////            System.out.println(method.getName());
//            if(endPoint.equals("/"+method.getName())) {
//                try {
//                    method.invoke(userController);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        for (Method method : methods) { // 메소드 갯수만큼 반복
            Annotation annotation = method.getDeclaredAnnotation(RequestMapping.class);
            RequestMapping requestMapping = (RequestMapping) annotation;
            System.out.println(requestMapping.value());

            if(requestMapping.value().equals(endPoint)) {
                try {
                    String path = (String) method.invoke(userController);

                    RequestDispatcher dis = request.getRequestDispatcher(path);
                    // 내부에서 실행하기 때문에 필터 안탐
                    dis.forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    break;
                }
            }
        }
    }
}
