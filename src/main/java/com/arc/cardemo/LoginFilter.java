package com.arc.cardemo;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.arc.cardemo.domain.AccountCredentials;
import com.arc.cardemo.domain.DbHelper;
import com.arc.cardemo.domain.User;
import com.arc.cardemo.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoginFilter extends AbstractAuthenticationProcessingFilter  {

	
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
		//logThis("login filter:  attempt");
		//logThis("user find login filter " + creds.getUsername());
		User user = dbLookUp.doLookup(creds.getUsername());
		if (user == null) {
			log.info("no user found returning null");
			return null;
		}

		//logThis("user find: " + user.getUsername());
		//need to test if user exists -- else we throw ugly exception

		Authentication authentication;
		authentication = getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
				creds.getUsername(), creds.getPassword(), Collections.emptyList()));
		return authentication;

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		AuthenticationService.addToken(res, auth.getName());
	}

	
}
