package com.example.jspblog.web;

import com.example.jspblog.domain.user.dto.JoinReqDto;
import com.example.jspblog.domain.user.dto.LoginReqDto;
import com.example.jspblog.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public class UserController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    // http://loalhost:8080/jspblog/user?cmd=XXX
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cmd = request.getParameter("cmd");
        UserService userService = new UserService();

        if(cmd.equals("loginForm")) {
            response.sendRedirect("user/loginForm.jsp");
        } else if (cmd.equals("login")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            LoginReqDto dto = new LoginReqDto();
            dto.setUsername(username);
            dto.setPassword(password);
            userService.로그인(dto);
        } else if (cmd.equals("joinForm")) {
            response.sendRedirect("user/joinForm.jsp");
        } else if (cmd.equals("join")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            JoinReqDto dto = new JoinReqDto();
            dto.setUsername(username);
            dto.setPassword(password);
            dto.setEmail(email);
            dto.setAddress(address);
            int result = userService.회원가입(dto);
            if (result == 1) {
                response.sendRedirect("index.jsp");
            } else {
                
            }
        }
    }
}
