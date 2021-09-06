package com.example.dispatcherservlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;

@WebServlet(name = "dispatcherServlet", urlPatterns = "/*")
public class MyDispatcherServlet extends HttpServlet {

    private final Map<String, Object> handlerMapping = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public MyDispatcherServlet() {
        initHandlerMapping();
        initHandlerAdapter();
    }

    private void initHandlerMapping() {
        Set<Class> classes = new HashSet<Class>();
        String packageName = "com.example.dispatcherservlet";
        String packageNameSlashed =
                "./" + packageName.replace(".", "/");
        URL packageDirURL =
                Thread.currentThread().getContextClassLoader().getResource(packageNameSlashed);
        String directoryString = packageDirURL.getFile();

        File directory = new File(directoryString);
        if(directory.exists()){
            String[] files = directory.list();
            for(String fileName : files){
                fileName = fileName.substring(0, fileName.length() - 6); // 확장자 삭제
                try{
                    Class c = Class.forName(packageName + "." + fileName); // Dynamic Loading
                    classes.add(c); // List<Class> list 에 넣는다
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        for (Class aClass : classes) {
            System.out.println("aClass = " + Arrays.toString(aClass.getDeclaredFields()));
        }
    }

    private void initHandlerAdapter() {
        handlerAdapters.add(new RequestMappingHandlerAdapter());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("my servlet!!");
    }
}
