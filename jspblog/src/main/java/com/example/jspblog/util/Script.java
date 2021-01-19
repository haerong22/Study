package com.example.jspblog.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Script {

    public static void back(HttpServletResponse response, String msg) {
        PrintWriter out;
        try {
            out = response.getWriter();
            out.println("<script>");
            out.println("alert('"+msg+"')");
            out.println("history.back()");
            out.println("</script>");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
