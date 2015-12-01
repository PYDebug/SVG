package edu.tongji.webgis.security;

import edu.tongji.webgis.exception.AppException;
import edu.tongji.webgis.utils.DataWrapper;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class AppContrllerAdvise {

	@ExceptionHandler(AppException.class)
	public void handleForbiddenException(HttpServletResponse response,
			AppException e) throws IOException {
		response.setStatus(e.getStatusCode());
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(
				new DataWrapper(e.getErrorCode(), e.getMessage()).toString());
	}
}
