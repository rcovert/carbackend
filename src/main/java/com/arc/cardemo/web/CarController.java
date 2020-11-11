package com.arc.cardemo.web;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arc.cardemo.domain.Car;
import com.arc.cardemo.domain.CarRepository;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
@RestController
public class CarController {

	@Autowired
	private CarRepository repository;
	

	@RequestMapping("/cars")
	public Iterable<Car> getCars() {
		return repository.findAll();
	}
	
	@GetMapping("/stream-sse")
	public Flux<ServerSentEvent<String>> streamEvents() {
		return Flux.interval(Duration.ofSeconds(4))
				.map(sequence -> ServerSentEvent.<String>builder().id(String.valueOf(sequence)).event("periodic-event")
						.data("SSE - " + LocalTime.now().toString()).build());
	}
	
	private String getTheData() throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = new JSONObject();
		String timeNow = Instant.now().toString();
		String toParse = "{\"message\":\"flux server event with server sent event ... @ " + timeNow + "\"}";
		JSONObject json = (JSONObject) parser.parse(toParse);
		String theData = json.toJSONString();
		return theData;
	}
	
	@GetMapping("/stream-sse2")
	public Flux<ServerSentEvent<String>> streamEvents2() {
		return Flux.interval(Duration.ofMillis(500))
				.map(sequence -> {
					try {
						return ServerSentEvent.<String>builder().id(String.valueOf(sequence)).event("message")
								.data(getTheData())
								.build();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				});
	}

	@GetMapping("/mono-sse")
	public Flux<ServerSentEvent<String>> singleEvent() throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = new JSONObject();
		String timeNow = Instant.now().toString();
		String toParse = "{\"message\":\"mono server event with server sent event ... @ " + timeNow + "\"}";
		JSONObject json = (JSONObject) parser.parse(toParse);
		//log.info(json.toJSONString());

		return Flux.interval(Duration.ofMillis(100))
				.just(ServerSentEvent.<String>builder().event("message")
				.data(json.toJSONString()).id("11").build());
	}

	@GetMapping(path = "/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> streamFlux() {
		return Flux.interval(Duration.ofSeconds(1)).map(sequence -> "Flux - " + LocalTime.now().toString());
	}
}