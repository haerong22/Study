package org.example;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import java.io.File;

public class WebApplicationServerLauncher {

    private static final Logger logger = LoggerFactory.getLogger(WebApplicationServerLauncher.class);

    public static void main(String[] args) throws LifecycleException {

        // 내장 톰캣
//        String appBase = "webapp/";
//
//        Tomcat tomcat = new Tomcat();
//        tomcat.setPort(8080);
//
//        tomcat.addWebapp("/", new File(appBase).getAbsolutePath());
//        logger.info("configuring app with basedir: {}", new File("./" + appBase).getAbsolutePath());
//
//        tomcat.start();
//        tomcat.getServer().await();

        String appBase = "webapp/";

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        Context context = tomcat.addContext("", new File(appBase).getAbsolutePath());
        HttpServlet calculatorServlet = new CalculatorServlet();
        String servletName = "calculate";
        String urlPattern = "/calculate";

        tomcat.addServlet("", servletName, calculatorServlet);
        context.addServletMappingDecoded(urlPattern, servletName);

        logger.info("configuring app with basedir: {}", new File("./" + appBase).getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
    }
}
