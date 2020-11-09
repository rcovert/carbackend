package com.arc.cardemo;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.arc.cardemo.domain.Car;
import com.arc.cardemo.domain.CarRepository;
import com.arc.cardemo.domain.Owner;
import com.arc.cardemo.domain.OwnerRepository;
import com.arc.cardemo.domain.User;
import com.arc.cardemo.domain.UserRepository;

import com.arc.cardemo.utils.LoggingHelper;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class CardemoApplication extends SpringBootServletInitializer {

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
		// displayAllBeans();
	}

	public static void displayAllBeans() {
		String[] allBeanNames = context.getBeanDefinitionNames();
		Arrays.sort(allBeanNames);

		for (String beanName : allBeanNames) {
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

@RestController
@RequiredArgsConstructor
class GreetingRestController {

	private final GreetingService greetingService;
	@GetMapping("/greeting/{name}")
	Mono<GreetingResponse> greet(@PathVariable String name) {
		return this.greetingService.greet(new GreetingRequest(name));
	}
	
	@GetMapping(path = "/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> streamFlux() {
	    return Flux.interval(Duration.ofSeconds(3))
	      .map(sequence -> "Flux - " + LocalTime.now().toString());
	}
	
	@GetMapping(path = "/mono-flux", produces = "text/event-stream")
	public Mono<String> monoFlux() {
	    return Mono.just("Mono - " + LocalTime.now().toString());
	}
	
	@GetMapping(path = "/mono-flux2")
	public Mono<UploadResponse> monoFlux2() {
	    return this.greetingService.mono2(new UploadResponse("Mono - " + LocalTime.now().toString()));
	}

}

@Service
class GreetingService {
	private GreetingResponse greet (String name) {
		return new GreetingResponse("Hello " + name + " @ " + Instant.now());
	}
	private UploadResponse monoX (String message) {
		return new UploadResponse("Hello " + message);
	}

	Flux<GreetingResponse> greetMany( GreetingRequest request) {
		return Flux.fromStream(Stream.generate(() -> greet(request.getName())))
				.delayElements(Duration.ofSeconds(1));
	}
	Mono<GreetingResponse> greet(GreetingRequest request) {
		return Mono.just(greet(request.getName()));
	}
	Mono<UploadResponse> mono2(UploadResponse resp) {
		return Mono.just(monoX(resp.getMessage()));
	}
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class GreetingResponse {
	private String message;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class GreetingRequest {
	private String name;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class UploadResponse {
	private String message;
}


