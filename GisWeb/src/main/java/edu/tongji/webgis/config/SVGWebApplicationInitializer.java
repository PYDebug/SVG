package edu.tongji.webgis.config;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Created by panyan on 15/11/26.
 */
@Order(3)
public class SVGWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer implements WebApplicationInitializer
{

    /*
      * 应用上下文，除web部分
      */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {AppConfig.class, SecurityConfig.class};
    }


    /*
          * web上下文
          */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{DispatcherConfig.class};
    }

    /*
          * DispatcherServlet的映射路径
          */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /*
      * 注册过滤器，映射路径与DispatcherServlet一致，路径不一致的过滤器需要注册到另外的WebApplicationInitializer中
      */
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[] {characterEncodingFilter};
    }
}
