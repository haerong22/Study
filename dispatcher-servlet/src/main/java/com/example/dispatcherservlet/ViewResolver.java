package com.example.dispatcherservlet;

import javax.servlet.http.HttpServletRequest;

public interface ViewResolver {

    View resolveViewName(String viewName) throws Exception;
}
