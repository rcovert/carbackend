package com.arc.cardemo;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.arc.cardemo.service.AuthenticationService;
import com.arc.cardemo.utils.LoggingHelper;

@Configuration
public class AuthenticationFilter extends GenericFilterBean {
	
	//@Autowired
	private LoggingHelper logThis = new LoggingHelper();
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		
		Authentication authentication = AuthenticationService.getAuthentication((HttpServletRequest) request);
		logThis.logData("auth filter:  inside of authentication filter");
		SecurityContextHolder.getContext().setAuthentication(authentication);

		filterChain.doFilter(request, response);
	}
}
