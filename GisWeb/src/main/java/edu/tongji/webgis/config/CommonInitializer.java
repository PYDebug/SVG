package edu.tongji.webgis.config;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.util.Log4jConfigListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Created by panyan on 15/11/26.
 */
@Order(1)
public class CommonInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //Log4jConfigListener
        servletContext.setInitParameter("log4jConfigLocation", "/WEB-INF/log4j.properties");
        servletContext.addListener(Log4jConfigListener.class);
    }
}
