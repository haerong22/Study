package com.example.dispatcherservlet.springmvc;

public interface ViewResolver {

    View resolveViewName(String viewName) throws Exception;
}
