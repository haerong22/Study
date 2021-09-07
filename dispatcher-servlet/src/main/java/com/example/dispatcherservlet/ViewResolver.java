package com.example.dispatcherservlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ViewResolver {

    boolean supportsView(String path);

    void render(HttpServletRequest request, HttpServletResponse response, String path);
}
