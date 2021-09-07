package com.example.dispatcherservlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcherServlet", urlPatterns = "/")
public class MyDispatcherServlet extends HttpServlet {

    private final ApplicationStartUp resources = ApplicationStartUp.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        Object handler = resources.getHandler(request);
        if (handler != null) {
            MyHandlerAdapter handlerAdapter = resources.getHandlerAdapter(handler);
            if (handlerAdapter != null) {
                handlerAdapter.handle(request, response, handler);
            }
        } else {
            request.getRequestDispatcher("/404.jsp").forward(request, response);
        }
    }
}
