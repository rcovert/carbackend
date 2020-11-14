package com.arc.cardemo.service;

import static java.util.Collections.emptyList;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuthenticationService {
	static final long EXPIRATIONTIME = 864_000_00; // 1 day in milliseconds
	static final String SIGNINGKEY = "SecretKey";
	static final String PREFIX = "Bearer";
	//

	// Add token to Authorization header
	static public void addToken(HttpServletResponse res, String username) {
		//logThis.logData("auth service:  adding token");
		String JwtToken = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SIGNINGKEY).compact();
		res.addHeader("Authorization", PREFIX + " " + JwtToken);
		res.addHeader("Access-Control-Expose-Headers", "Authorization");
	}

	// Get token from Authorization header
	static public Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		//logThis.logData("token is " + token);
		String sseUser = request.getParameter("user");
		//logThis.logData("auth service sseUser is " + sseUser);
		//logThis.logData("auth service:  getting authentication from service");
		if (token != null) {
			String user = Jwts.parser().setSigningKey(SIGNINGKEY).parseClaimsJws(token.replace(PREFIX, "")).getBody()
					.getSubject();
			//logThis.logData("auth service user is: " + user);

			if (user != null)
				return new UsernamePasswordAuthenticationToken(user, null, emptyList());
		} else if (sseUser != null) {
			return new UsernamePasswordAuthenticationToken("admin", null, emptyList());
		}
		
		return null;
	}
}
