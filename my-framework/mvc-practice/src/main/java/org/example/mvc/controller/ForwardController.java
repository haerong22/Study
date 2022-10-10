package org.example.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements Controller {
    private final String forwardUriPath;

    public ForwardController(String forwardUriPath) {
        this.forwardUriPath = forwardUriPath;
    }

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return forwardUriPath;
    }
}
