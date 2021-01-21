package com.example.jspblog.web;

import com.example.jspblog.domain.user.User;
import com.example.jspblog.domain.user.dto.JoinReqDto;
import com.example.jspblog.domain.user.dto.LoginReqDto;
import com.example.jspblog.service.UserService;
import com.example.jspblog.util.Script;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

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

        switch (cmd) {
            case "loginForm":
                request.getRequestDispatcher("user/loginForm.jsp").forward(request, response);
                break;
            case "login": {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                LoginReqDto dto = new LoginReqDto();
                dto.setUsername(username);
                dto.setPassword(password);
                User userEntity = userService.로그인(dto);
                if (userEntity != null) {
                    request.getSession().setAttribute("principal", userEntity);
                    response.sendRedirect("index.jsp");
                } else {
                    Script.back(response, "로그인 실패");
                }
                break;
            }
            case "joinForm":
                request.getRequestDispatcher("user/joinForm.jsp").forward(request, response);
                break;
            case "join": {
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
                    Script.back(response, "회원가입실패");
                }
                break;
            }
            case "usernameCheck": {
                BufferedReader br = request.getReader();
                String username = br.readLine();
                int result = userService.유저네임중복체크(username);
                PrintWriter out = response.getWriter();
                if (result == 1) {
                    out.print("ok");
                } else {
                    out.print("fail");
                }
                break;
            }
            case "logout": {
                request.getSession().invalidate();
                response.sendRedirect("index.jsp");
            }
        }
    }
}
