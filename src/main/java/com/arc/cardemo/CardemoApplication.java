package com.arc.cardemo;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.arc.cardemo.domain.Car;
import com.arc.cardemo.domain.CarRepository;
import com.arc.cardemo.domain.Owner;
import com.arc.cardemo.domain.OwnerRepository;
import com.arc.cardemo.domain.User;
import com.arc.cardemo.domain.UserRepository;
import com.arc.cardemo.utils.LoggingHelper;

@SpringBootApplication
public class CardemoApplication {

	@Autowired
	private CarRepository repository;
	@Autowired
	private OwnerRepository orepository;
	@Autowired
	private UserRepository urepository;
	// @Autowired
	private static LoggingHelper logThis = new LoggingHelper();
	@Autowired
	private static ApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(CardemoApplication.class, args);
		//UserRepository urep2 = (UserRepository) context.getBean("userRepository");
		//User user = urep2.findByUsername("admin");
		//logThis.logData("from main user name is " + user.getUsername());
		//displayAllBeans();
	}
	
	public static void displayAllBeans() {
        String[] allBeanNames = context.getBeanDefinitionNames();
        Arrays.sort(allBeanNames);
        
        for(String beanName : allBeanNames) {
            System.out.println(beanName);
        }

    }

	@Bean
	CommandLineRunner runner() {
		return args -> {
			// Add owner objects and save these to db
			Owner owner1 = new Owner("John", "Johnson");
			Owner owner2 = new Owner("Mary", "Robinson");
			orepository.save(owner1);
			orepository.save(owner2);

			// Add car object with link to owners and save these to db.
			Car car = new Car("Ford", "Mustang", "Red", "ADF-1121", 2017, 59000, owner1);
			repository.save(car);
			car = new Car("Nissan", "Leaf", "White", "SSJ-3002", 2014, 29000, owner2);
			repository.save(car);
			car = new Car("Toyota", "Prius", "Silver", "KKO-0212", 2018, 39000, owner2);
			repository.save(car);

			// username: user password: user
			urepository.save(new User("user", "$2a$04$1.YhMIgNX/8TkCKGFUONWO1waedKhQ5KrnB30fl0Q01QKqmzLf.Zi", "USER"));
			// username: admin password: admin
			urepository
					.save(new User("admin", "$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", "ADMIN"));

			logThis.logData("Spring Boot example log record");
		};

	}
}
