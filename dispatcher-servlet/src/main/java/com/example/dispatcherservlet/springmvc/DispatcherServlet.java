package com.example.dispatcherservlet.springmvc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "dispatcherServlet", urlPatterns = "/")
public class DispatcherServlet extends HttpServlet {

    private List<ViewResolver> viewResolvers;
    private List<HandlerAdapter> handlerAdapters;
    private Map<String, MappingRegistry> handlerMapping;

    public DispatcherServlet() {
        WebApplicationContext resources = WebApplicationContext.getInstance();
        this.viewResolvers = resources.getViewResolvers();
        this.handlerMapping = resources.getHandlerMapping();
        this.handlerAdapters = resources.getHandlerAdapters();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        try {
            MappingRegistry handler = getHandler(request);
            if (handler != null) {
                HandlerAdapter handlerAdapter = getHandlerAdapter(handler);
                if (handlerAdapter != null) {
                    String result = handlerAdapter.handle(request, response, handler);
                    if (result != null) {
                        render(request, response, result);
                    }
                }
            } else {
                request.getRequestDispatcher("/404.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MappingRegistry getHandler(HttpServletRequest request) {
        return handlerMapping.get(request.getRequestURI());
    }

    private HandlerAdapter getHandlerAdapter(MappingRegistry handler) {
        for (HandlerAdapter handlerAdapter : handlerAdapters) {
            if (handlerAdapter.supports(handler.getHandler())) {
                return handlerAdapter;
            }
        }
        return null;
    }

    private void render(HttpServletRequest request, HttpServletResponse response, String result) throws Exception {
        View view = resolveViewName(result);
        if (view != null) {
            view.render(request, response, result);
        }
    }

    private View resolveViewName(String path) throws Exception {

        for (ViewResolver viewResolver : viewResolvers) {
            View view = viewResolver.resolveViewName(path);
            if (view != null) {
                return view;
            }
        }
        return null;
    }
}
