package com.example.dispatcherservlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyViewResolver implements ViewResolver{
    @Override
    public boolean supportsView(String path) {
        return path.endsWith(".jsp");
    }

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response, String path) {
        try {
            request.getRequestDispatcher(path).forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
