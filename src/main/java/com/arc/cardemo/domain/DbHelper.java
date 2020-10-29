package com.arc.cardemo.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import com.arc.cardemo.SpringApplicationContext;
import com.arc.cardemo.utils.LoggingHelper;

@EnableJpaRepositories
@Component
public class DbHelper {
	@Autowired
	private UserRepository repository;
	@Autowired
	private static ApplicationContext context;
	// the two objects above did not work
	// had to create bridge class SpringAppContext to get static reference to context
	// not sure why @Autowired does not work with the logger seems to work in
	// some situations but not others
	private LoggingHelper logThis = new LoggingHelper();

	public User doLookup(String userName) {
		displayAllBeans();
		UserRepository urep3 = (UserRepository) SpringApplicationContext.getBean("userRepository");
		User currentUser = urep3.findByUsername(userName);
		if (currentUser == null) {
			logThis.logData("dbhelper: no user found");
		} else {
			logThis.logData("dbhelper:  name from dbLook --> " + currentUser.getUsername());
		}

		return currentUser;
	}

	public void displayAllBeans() {
		UserRepository urep9 = (UserRepository) SpringApplicationContext.getBean("userRepository");
        //context.containsBean("userRepository");

    }
}

