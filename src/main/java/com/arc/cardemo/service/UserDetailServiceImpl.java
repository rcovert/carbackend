package com.arc.cardemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.arc.cardemo.domain.User;
import com.arc.cardemo.domain.UserRepository;
import com.arc.cardemo.utils.LoggingHelper;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository repository;
	@Autowired
	private static LoggingHelper logThis = new LoggingHelper();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logThis.logData("made it to loadUserByUsername");
		// logThis.logData("user details:  load user by name start");
		User currentUser = repository.findByUsername(username);
		if (currentUser == null) {
			logThis.logData("user details:  user not found error");
		}
		UserDetails user = new org.springframework.security.core
				.userdetails.User(username, currentUser.getPassword(),
				true, true, true, true, AuthorityUtils.createAuthorityList(currentUser.getRole()));
		logThis.logData("user details:  load user by name finish");
		return user;
	}

}
