package com.example.dispatcherservlet;

import com.example.dispatcherservlet.annotation.Controller;
import com.example.dispatcherservlet.annotation.RequestMapping;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class WebApplicationContext {

    public static WebApplicationContext instance;

    private final Map<String, Object> handlerMapping = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();
    private final List<ViewResolver> viewResolvers = new ArrayList<>();

    public static WebApplicationContext getInstance() {
       if (instance == null) {
           instance = new WebApplicationContext();
       }
       return instance;
    }

    private WebApplicationContext()  {
        initResources();
    }

    public Map<String, Object> getHandlerMapping() {
        return handlerMapping;
    }

    public List<MyHandlerAdapter> getHandlerAdapters() {
        return handlerAdapters;
    }

    public List<ViewResolver> getViewResolvers() {
        return viewResolvers;
    }

    private void initResources() {
        String packageName = "com.example.dispatcherservlet";
        Set<Class<?>> classes = new HashSet<>();
        setClasses(classes, packageName);

        for (Class<?> aClass : classes) {
            try {
                Object instance = aClass.getConstructor().newInstance();
                if (instance instanceof MyHandlerAdapter) {
                    handlerAdapters.add((MyHandlerAdapter) instance);
                }
                if (instance instanceof ViewResolver) {
                    viewResolvers.add((ViewResolver) instance);
                }
                if (aClass.isAnnotationPresent(Controller.class)) {
                    Method[] declaredMethods = aClass.getDeclaredMethods();
                    for (Method declaredMethod : declaredMethods) {
                        RequestMapping annotation = declaredMethod.getAnnotation(RequestMapping.class);
                        handlerMapping.put(annotation.value(), instance);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setClasses(Set<Class<?>> classes, String packageName) {
        String directoryString = getDirectoryString(packageName);
        File directory = new File(directoryString);
        if(directory.exists()){
            String[] files = directory.list();
            for(String fileName : files){
                if (isExcluded(fileName)) continue;
                if (fileName.endsWith(".class")) {
                    fileName = fileName.substring(0, fileName.length() - 6);
                    try{
                        Class<?> c = Class.forName(packageName + "." + fileName);
                        if (!c.isInterface()) {
                            classes.add(c);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    setClasses(classes, packageName + "." +  fileName);
                }
            }
        }
    }

    private boolean isExcluded(String fileName) {
        String[] exclude = {"ApplicationStartUp", "MyDispatcherServlet"};
        for (String s : exclude) {
            if (fileName.contains(s)) return true;
        }
        return false;
    }

    private String getDirectoryString(String packageName) {
        String packageNameSlashed =
                "./" + packageName.replace(".", "/");
        URL packageDirURL =
                Thread.currentThread().getContextClassLoader().getResource(packageNameSlashed);
        return packageDirURL.getFile();
    }
}
