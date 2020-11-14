package com.arc.cardemo.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import com.arc.cardemo.SpringApplicationContext;

import lombok.extern.log4j.Log4j2;

@Log4j2
@EnableJpaRepositories
@Component
public class DbHelper {
	@Autowired
	private UserRepository repository;
	@Autowired
	private static ApplicationContext context;
	// the two objects above did not work
	// had to create bridge class SpringAppContext to get static reference to context

	public User doLookup(String userName) {
		UserRepository urep3 = (UserRepository) SpringApplicationContext.getBean("userRepository");
		User currentUser = urep3.findByUsername(userName);
		if (currentUser == null) {
			log.info("dbhelper: no user found");
		} else {
			log.info("dbhelper:  name from dbLook --> " + currentUser.getUsername());
		}

		return currentUser;
	}

}

