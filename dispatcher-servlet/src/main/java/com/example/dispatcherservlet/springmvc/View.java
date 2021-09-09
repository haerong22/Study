package com.example.dispatcherservlet.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface View {

    void render(HttpServletRequest request, HttpServletResponse response, String path);
}
