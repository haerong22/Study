package com.example.dispatcherservlet.springmvc;

import java.lang.reflect.Method;

public class MappingRegistry {

    private Object handler;
    private Method method;

    public MappingRegistry() {
    }

    public MappingRegistry(Object handler, Method method) {
        this.handler = handler;
        this.method = method;
    }

    public Object getHandler() {
        return handler;
    }

    public Method getMethod() {
        return method;
    }
}
