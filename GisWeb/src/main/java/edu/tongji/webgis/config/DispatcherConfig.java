package edu.tongji.webgis.config;

import edu.tongji.webgis.security.AccountAuthorizeInterceptor;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages = "edu.tongji.webgis.controller")
@EnableWebMvc
//@EnableAspectJAutoProxy(proxyTargetClass=true)
public class DispatcherConfig extends WebMvcConfigurerAdapter{

	private static final Logger logger = Logger
			.getLogger(DispatcherConfig.class);

	/*
	<property name="prefix" value="/WEB-INF/view/"/>
		<property name="suffix" value=".jsp"/>
		<property name="order" value="2"/>
	 */
	@Bean
	public ViewResolver viewResolver(){
		logger.info("InternalResourceViewResolver");
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(2);
		return viewResolver;
	}

	@Bean
	public CommonsMultipartResolver multipartResolver(){
		logger.info("CommonsMultipartResolver");
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("utf-8");
		commonsMultipartResolver.setMaxUploadSize(10000000L);
		return commonsMultipartResolver;
	}

	@Bean
	public ViewResolver svgResolver(){
		logger.info("svgResolver");
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/resource/svg/");
		internalResourceViewResolver.setOrder(1);
		return internalResourceViewResolver;
	}

	@Bean
	public ViewResolver iconResolver(){
		logger.info("iconResolver");
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/resource/icon/");
		internalResourceViewResolver.setOrder(1);
		return internalResourceViewResolver;
	}

	@Bean
	public ViewResolver pngResolver(){
		logger.info("pngResolver");
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/resource/png/");
		internalResourceViewResolver.setOrder(1);
		return internalResourceViewResolver;
	}

	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccountAuthorizeInterceptor());
    }
	
	 @Override
	 public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		 configurer.enable();
	 }

}
