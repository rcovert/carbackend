//package com.arc.cardemo.web;
//
//import static org.springframework.web.reactive.function.server.RouterFunctions.route;
//import static org.springframework.web.reactive.function.server.ServerResponse.ok;
//
//import java.time.Duration;
//import java.time.Instant;
//import java.util.stream.Stream;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.CacheControl;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@Log4j2
//@RestController
//public class GreetingController {
//	
//	@Bean
//	RouterFunction<ServerResponse> routes(GreetingService gs) {
//		log.info("made it greeting controller");
//		return route()
//				.GET("/greeting/{name}", r ->
//						ServerResponse.ok()
//						.contentType(MediaType.TEXT_EVENT_STREAM)
//						.header("Access-Control-Allow-Origin", "*")
//						.body(gs.greet(new GreetingRequest(r.pathVariable("name"))), GreetingResponse.class))
//				.GET("/greetings/{name}", r -> ok()
//						.contentType(MediaType.TEXT_EVENT_STREAM)
//						.cacheControl(CacheControl.noCache())
//						.header("Access-Control-Allow-Origin", "*")
//						.body(gs.greetMany(new GreetingRequest(r.pathVariable("name"))), GreetingResponse.class)
//				)
//				.build();
//	}
//
//}
//
////@Log4j2
////@RestController
////@RequiredArgsConstructor
////class GreetingRestController {
////
////	private final GreetingService greetingService;
////
////	@GetMapping(value = "/greetx")
////	String sendResponse() {
////		log.info("made it to greeting controller x");
////		return "{object: {name: 'rocky'}}";
////	}
////
////	@GetMapping("/test3")
////	public UploadResult test3() {
////		log.info("made it to test 3");
////		String value = "hello, test with data ";
////		UploadResult upload = new UploadResult(value);
////		return upload;
////	}
////
////	@GetMapping("/test")
////	public String test() {
////		String value = "hello, test with data []";
////		return value;
////	}
////
////	@Data
////	public static class UploadResult {
////		private String value;
////
////		public UploadResult(final String value) {
////			this.value = value;
////		}
////	}
////}
//
//@Service
//class GreetingService {
//	GreetingResponse greet(String name) {
//		return new GreetingResponse("Hello " + name + " @ " + Instant.now());
//	}
//
//	Flux<GreetingResponse> greetMany(GreetingRequest request) {
//		return Flux.fromStream(Stream.generate(() -> greet(request.getName()))).delayElements(Duration.ofSeconds(1));
//	}
//
//	Mono<GreetingResponse> greet(GreetingRequest request) {
//		return Mono.just(greet(request.getName()));
//	}
//}
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//class GreetingResponse {
//	private String message;
//}
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//class GreetingRequest {
//	private String name;
//}
