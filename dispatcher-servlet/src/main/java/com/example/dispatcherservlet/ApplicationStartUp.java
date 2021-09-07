package com.example.dispatcherservlet;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class ApplicationStartUp {

    public static ApplicationStartUp instance;

    private final Map<String, Object> handlerMapping = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();
    private final List<ViewResolver> viewResolvers = new ArrayList<>();

    public static ApplicationStartUp getInstance() {
       if (instance == null) {
           return new ApplicationStartUp();
       }
       return instance;
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

    private ApplicationStartUp()  {
        initResources();
    }

    public Object getHandler(HttpServletRequest request) {
        return handlerMapping.get(request.getRequestURI());
    }

    public MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter handlerAdapter : handlerAdapters) {
            if (handlerAdapter.supports(handler)) {
                return handlerAdapter;
            }
        }
        return null;
    }

    public ViewResolver getViewResolver(String path) {
        for (ViewResolver viewResolver : viewResolvers) {
            if (viewResolver.supportsView(path)) {
                return viewResolver;
            }
        }
        return null;
    }

    private void initResources() {
        Set<Class<?>> classes = new HashSet<>();
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
                fileName = fileName.substring(0, fileName.length() - 6);
                try{
                    Class<?> c = Class.forName(packageName + "." + fileName);
                    classes.add(c);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
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
            }
        }
    }
}
