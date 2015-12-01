package edu.tongji.webgis.security;

import edu.tongji.webgis.utils.DataWrapper;
import edu.tongji.webgis.utils.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		// unauthenticated request not in permit list finally goes here
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(
				new DataWrapper(ErrorCode.UNAUTHORIZED,
						"Provide token or Post username & password.")
						.toString());
	}
}
