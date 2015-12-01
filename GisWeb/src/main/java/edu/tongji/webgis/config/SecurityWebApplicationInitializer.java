package edu.tongji.webgis.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.WebApplicationInitializer;

@Order(2)
public class SecurityWebApplicationInitializer extends
		AbstractSecurityWebApplicationInitializer implements WebApplicationInitializer {
	//session监听器
//	@Override
//	protected boolean enableHttpSessionEventPublisher() {
//		return true;
//	}
}