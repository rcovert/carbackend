//package com.arc.cardemo.filter;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.CacheControl;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import reactor.util.function.Tuple2;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletResponse;
//import javax.servlet.ServletRequest;
//import javax.servlet.FilterChain;
//import javax.servlet.http.HttpServletRequest;
//
//import static org.springframework.web.reactive.function.server.RouterFunctions.route;
//import static org.springframework.web.reactive.function.server.ServerResponse.ok;
//
//import java.io.IOException;
//import java.time.Duration;
//import java.time.Instant;
//import java.util.Date;
//import java.util.stream.Stream;
//
//@Component
//public class ExampleFilter implements Filter {
//	private static final Logger logger = LoggerFactory.getLogger(ExampleFilter.class);
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//	}
//
//	@Override
//	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
//			throws IOException, ServletException {
//		logger.info("filter: " + ((HttpServletRequest) servletRequest).getMethod());
//		String methodString = ((HttpServletRequest) servletRequest).getMethod();
//
//		filterChain.doFilter(servletRequest, servletResponse);
//	}
//
//	@Override
//	public void destroy() {
//	}
//
//}
//
////@RestController
////@RequiredArgsConstructor
////class GreetingRestController {
////
////	private final GreetingService greetingService;
////
////	@GetMapping("/greeting/{name}")
////	Mono<GreetingResponse> greet1(@PathVariable String name) {
////		System.out.println("made it to api service");
////		return this.greetingService.greet(new GreetingRequest(name));
////	}
////	
////	@GetMapping("/test3")
////    public String test3() {
////		System.out.println("made it to test 3");
////        String value = "hello, test with data "; 
////        UploadResult upload = new UploadResult(value);
////        return upload.value;
////    }
////	
////	@GetMapping("/test")
////	public String test() {
////		String value = "hello, test with data []";
////		return value;
////	}
//////    public @ResponseBody UploadResult test() {
//////      String value = "hello, test with data []"; 
//////      return new UploadResult(value);
//////    }
////	
////	@Data
////	public static class UploadResult {
////        private String value;
////        public UploadResult(final String value)
////        {
////            this.value = value;
////        }
////    }
////}
////
//
////@Service
////class GreetingService {
////	GreetingResponse greet(String name) {
////		return new GreetingResponse("Hello " + name + " @ " + Instant.now());
////	}
////
////	Flux<GreetingResponse> greetMany(GreetingRequest request) {
////		return Flux.fromStream(Stream.generate(() -> greet(request.getName()))).delayElements(Duration.ofSeconds(1));
////	}
////
////	Mono<GreetingResponse> greet(GreetingRequest request) {
////		return Mono.just(greet(request.getName()));
////	}
////}
//
////@RestController
////@RequiredArgsConstructor
////class InformController {
////
////	private final InformService informService;
////	
////	private final GreetingService greetingService;
////
////	@GetMapping("/inform")
////	Mono<GreetingResponse> greet() {
////		return this.greetingService.greet(new GreetingRequest("request client"));
////	}
////
//////	@GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
//////	public Flux<InformTransaction> informTransactionEvents() {
//////		return informService.getInformTransactions();
//////	}
////}
////
////@Service
////class InformService {
////    Flux<InformTransaction> getInformTransactions() {
////        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
////        //interval.subscribe((i) -> informList.forEach(stock -> stock.setPrice(changePrice(stock.getPrice()))));
////
////        Flux<InformTransaction> informTransactionFlux = Flux.fromStream(Stream.generate(() -> new InformTransaction("Transaction Informtion", new Date())));
////        return Flux.zip(interval, informTransactionFlux).map(Tuple2::getT2);
////    }
////}
////
////@Data
////@AllArgsConstructor
////@NoArgsConstructor
////class InformTransaction {
////	String message;
////    Date when;
////}
//
////@Data
////@AllArgsConstructor
////@NoArgsConstructor
////class GreetingResponse {
////	private String message;
////}
////
////@Data
////@AllArgsConstructor
////@NoArgsConstructor
////class GreetingRequest {
////	private String name;
////}
