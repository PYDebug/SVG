package edu.tongji.webgis.security;

import edu.tongji.webgis.utils.DataWrapper;
import edu.tongji.webgis.utils.ErrorCode;
import edu.tongji.webgis.utils.MD5Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class TokenAuthenticationFilter extends GenericFilterBean {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	TokenHandler tokenHandler;
	// @Autowired AccountService as;

	private static final String HEADER_TOKEN = "X-Auth-Token";
	private static final String HEADER_USERNAME = "X-Username";
	private static final String HEADER_PASSWORD = "X-Password";

	private static final long EXPIRE_INTERVAL = 10 * 24 * 60 * 60 * 1000;

	private String signOutUrl;
	private String signInUrl;
	private String resourceUrl;

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpResp = (HttpServletResponse) response;

		long current = new Date().getTime();
		String currentUrl = new UrlPathHelper()
				.getPathWithinApplication(httpReq);

		String token = httpReq.getHeader(HEADER_TOKEN);

		// check URL in spring-security.properties
		Assert.hasText(signInUrl, "sign in url must be set");
		Assert.hasText(signOutUrl, "sign out url must be set");

		String username = httpReq.getHeader(HEADER_USERNAME);
		String password = httpReq.getHeader(HEADER_PASSWORD);
		if (currentUrl.equals(signInUrl)
				&& !httpReq.getMethod().equals("DELETE")) {
			if (username != null && password != null
					&& httpReq.getMethod().equals("POST")) {
				// check username and password
				password = MD5Tool.getMd5(password);
				Authentication auth = new UsernamePasswordAuthenticationToken(
						username, password);
				try {
					auth = authenticationManager.authenticate(auth);
					SecurityContextHolder.getContext().setAuthentication(auth);

					if (auth.getPrincipal() != null) {
						AccountUserDetails details = (AccountUserDetails) auth
								.getPrincipal();
						details.eraseCredentials();
						// refresh expires
						details.setExpires(current + EXPIRE_INTERVAL);
						// as.updateExpires(details.getId(),
						// details.getExpires());
						// generate token and set response header
						httpResp.setHeader(HEADER_TOKEN,
								tokenHandler.createTokenForAccount(details));
						setJsonResponse(httpResp, HttpServletResponse.SC_OK,
								new DataWrapper(details.getAccount()));
					}
				} catch (AuthenticationException e) {
					// e.printStackTrace();
					setJsonResponse(httpResp,
							HttpServletResponse.SC_UNAUTHORIZED,
							new DataWrapper(ErrorCode.UNAUTHORIZED,
									"Username or password wrong."));
				}
				// chain.doFilter(request, response);
			} else {
				setJsonResponse(httpResp, HttpServletResponse.SC_UNAUTHORIZED,
						new DataWrapper(ErrorCode.UNAUTHORIZED,
								"Failed to login."));
			}
		}else if (currentUrl.startsWith(resourceUrl)) {
			chain.doFilter(request, response);
		}else if (token != null) {
			// check token
			AccountUserDetails userDetails = tokenHandler
					.parseAccountFromToken(token);
			if (userDetails != null) {
				// check expires
				if (current >= userDetails.getExpires()) {
					setJsonResponse(httpResp,
							HttpServletResponse.SC_UNAUTHORIZED,
							new DataWrapper(ErrorCode.UNAUTHORIZED,
									"Token expired."));
				} else {
					// // extract logout URL in spring-security.properties file
					if (currentUrl.equals(signOutUrl)) {
						// Do Nothing...
						// // reset expires
						// as.updateExpires(userDetails.getId(), 0);
						setJsonResponse(httpResp, HttpServletResponse.SC_OK,
								new DataWrapper("Logout."));
						// SecurityContextHolder.clearContext();
					} else {
						UsernamePasswordAuthenticationToken securityToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						SecurityContextHolder.getContext().setAuthentication(
								securityToken);
						chain.doFilter(request, response);
					}
				}
			} else {
				setJsonResponse(httpResp, HttpServletResponse.SC_UNAUTHORIZED,
						new DataWrapper(ErrorCode.UNAUTHORIZED, "Token wrong."));
			}
		} else {
			setJsonResponse(httpResp, HttpServletResponse.SC_UNAUTHORIZED,
					new DataWrapper(ErrorCode.UNAUTHORIZED,
							"Token not provided."));
		}
	}

	public void setSignOutUrl(String signOutUrl) {
		this.signOutUrl = signOutUrl;
	}

	public void setSignInUrl(String signInUrl) {
		this.signInUrl = signInUrl;
	}

	public void setResourceUrl(String resourceUrl){this.resourceUrl = resourceUrl;}

	// public void setSignUpUrl(String signUpUrl) {
	// this.signUpUrl = signUpUrl;
	// }

	private void setJsonResponse(HttpServletResponse response, int stauts,
			DataWrapper ret) {
		response.setStatus(stauts);
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(ret.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setAuthenticationManager(
			AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

}
