package com.example.jspblog.test;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ajax2")
public class Ajax2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader br = request.getReader();
        String data = br.readLine();
        System.out.println("data : " + data);

        Gson gson = new Gson();
        // gson.fromJson() : json -> java object
        // gson.toJson() : java object -> json
        TestDto dto = gson.fromJson(data, TestDto.class);
        System.out.println("dto : " + dto);

        String userJson = gson.toJson(dto);
        System.out.println("userJson : " + userJson);
        PrintWriter out = response.getWriter();
        out.print(userJson);
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}

class TestDto {
    private String username;
    private String password;

    @Override
    public String toString() {
        return "TestDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}