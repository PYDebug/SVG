package edu.tongji.webgis.utils;


import edu.tongji.webgis.model.User;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RequiredRole {
	User.Role[] value();
	
//	boolean privateAccess() default false;
}
