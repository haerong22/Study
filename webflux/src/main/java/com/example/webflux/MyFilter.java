package com.example.webflux;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter implements Filter {

    private EventNotify eventNotify;

    public MyFilter(EventNotify eventNotify) {
        this.eventNotify = eventNotify;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("필터 실행");

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("text/event-stream; charset=utf-8");

        PrintWriter out = response.getWriter();
        for (int i = 0; i < 5 ; i++) {
            out.println("응답 : " + i);
            out.flush();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (true) {
            try {
                if(eventNotify.getChange()) {
                    int lastIndex = eventNotify.getEvents().size() -1;
                    out.println("응답 : " + eventNotify.getEvents().get(lastIndex));
                    out.flush();
                    eventNotify.setChange(false);
                }
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
