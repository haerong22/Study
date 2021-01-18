package com.example.jspblog.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

//localhost:8080/blog/test
@WebServlet("/test")
public class ApiServerTest extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String mime = request.getContentType();
        System.out.println(mime);

        if(mime.equals("application/json")) {
            BufferedReader br = request.getReader();
            String input;
            StringBuffer buffer = new StringBuffer();
            while((input = br.readLine()) != null) {
                buffer.append(input);
            }
            System.out.println(buffer.toString());
        } else {
            String food = request.getParameter("food");
            String recipe = request.getParameter("recipe");
        }

        // 응답시 mime 타입 정의
        response.setContentType("text/html; charset=utf-8");

        int result = 1;
        PrintWriter out = response.getWriter();
        if(result == 1) {
            out.println(result);
//            out.println("{\"food\":"+ food + ",\"recipe\":" + recipe + "}");
        } else {
            out.println("{\"error\":\"fail\"}");
        }
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
