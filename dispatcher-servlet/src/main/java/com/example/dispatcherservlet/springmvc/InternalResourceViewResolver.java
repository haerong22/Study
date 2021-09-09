package com.example.dispatcherservlet.springmvc;

public class InternalResourceViewResolver implements ViewResolver{

    @Override
    public View resolveViewName(String viewName) {
        return viewName.endsWith(".jsp") ? new InternalResourceView() : null;
    }
}

