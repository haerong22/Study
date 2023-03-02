package hello.container;

import hello.servlet.HelloServlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;

public class AppInitV1Servlet implements AppInit {
    @Override
    public void onStartup(ServletContext servletContext) {
        System.out.println("AppInitV1Servlet.onStartup");

        // 서블릿 코드 등록
        ServletRegistration.Dynamic helloServlet =
                servletContext.addServlet("helloServlet", new HelloServlet());

        helloServlet.addMapping("/hello-servlet");
    }
}
