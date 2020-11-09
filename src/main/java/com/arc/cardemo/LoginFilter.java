package com.arc.cardemo;

import java.io.IOException;

import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.security.core.userdetails.UserDetails;

import com.arc.cardemo.domain.AccountCredentials;
import com.arc.cardemo.domain.DbHelper;
import com.arc.cardemo.service.AuthenticationService;
import com.arc.cardemo.domain.User;
import com.arc.cardemo.utils.LoggingHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class LoginFilter extends AbstractAuthenticationProcessingFilter  {

	// @Autowired
	private LoggingHelper logThis = new LoggingHelper();
	// @Autowired
	private DbHelper dbLookUp = new DbHelper();

	public LoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException, IOException, ServletException {
		AccountCredentials creds = new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);
		logThis("login filter:  attempt");
		logThis("user find login filter " + creds.getUsername());
		User user = dbLookUp.doLookup(creds.getUsername());
		if (user == null) {
			logThis("no user found returning null");
			return null;
		}

		logThis("user find: " + user.getUsername());
		//need to test if user exists -- else we throw ugly exception

		Authentication authentication;
		authentication = getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
				creds.getUsername(), creds.getPassword(), Collections.emptyList()));
		return authentication;

//		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),
//				creds.getPassword(), Collections.emptyList()));
	}

	private void logThis(String string) {
		// TODO Auto-generated method stub
		logThis.logData("from login filter:  " + string);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		AuthenticationService.addToken(res, auth.getName());
	}

	
}
