package com.arc.cardemo.web;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.arc.cardemo.domain.Car;
import com.arc.cardemo.domain.CarRepository;
import com.arc.cardemo.messaging.MyEvent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
@RestController
public class CarController {

	private String EventData;
	
	static final long SSE_SESSION_TIMEOUT = 15 * 60 * 1000L;
	private final Set<SseEmitter> clients = new CopyOnWriteArraySet<>();


	public String getEventData() {
		return EventData;
	}

	public void setEventData(String eventData) {
		EventData = eventData;
	}

	// this will register the bean as a listener
	// async will spawn a new thread
	@Async
	@EventListener
	public void onMyEvent(MyEvent event) {
		log.info(event.getMsg());
		setEventData(event.getMsg());

		List<SseEmitter> deadEmitters = new ArrayList<>();
		clients.forEach(emitter -> {
			try {
				EventResponse er = new EventResponse(this.getEventData());
				emitter.send(er);
				//log.info("Sent to client, took: {}", Duration.between(start, Instant.now()));
			} catch (Exception ignore) {
				deadEmitters.add(emitter);
			}
		});
		clients.removeAll(deadEmitters);
	}

	// this is the event source for the client apps
	@RequestMapping(value = "/stream-sse4", method = RequestMethod.GET)
	public SseEmitter events(HttpServletRequest request) {
		log.info("SSE stream opened for client: " + request.getRemoteAddr());
		SseEmitter emitter = new SseEmitter(SSE_SESSION_TIMEOUT);
		clients.add(emitter);

		// Remove SseEmitter from active clients on error or client disconnect
		emitter.onTimeout(() -> {
			log.info("client timed out..." + emitter.hashCode());
			emitter.complete();
			clients.remove(emitter);
		});
		emitter.onError((e) -> {
			log.info("client in error state.. removing client");
			clients.remove(emitter);
		});
		emitter.onCompletion(() -> clients.remove(emitter));
		return emitter;
	}
	
	@ExceptionHandler(value = AsyncRequestTimeoutException.class)
	   public ModelAndView handleTimeout(HttpServletResponse rsp) throws IOException {
	      if (!rsp.isCommitted()) {
	         rsp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
	      }
	      return new ModelAndView();
	   }

//	don't need this since rest repository handles all requests @RequestMapping("/cars")
//	public Iterable<Car> getCars() {
//		log.info("this may be the place to use events after call to repository");
//		return repository.findAll();
//	}

	@GetMapping("/stream-sse")
	public Flux<ServerSentEvent<String>> streamEvents() {
		return Flux.interval(Duration.ofSeconds(4))
				.map(sequence -> ServerSentEvent.<String>builder().id(String.valueOf(sequence)).event("periodic-event")
						.data("SSE - " + LocalTime.now().toString()).build());
	}


	@RequestMapping("/stream-sse3")
	@ResponseBody
	public SseEmitter getPricing() {
		SseEmitter emitter = new SseEmitter();
		log.info("setting sse emitter...");
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int x = 0; x < 20; x++) {
					try {
						String ji = new Integer(new Random().nextInt(10) + 1).toString();
						EventResponse gr = new EventResponse(ji);
						// emitter.send(new Random().nextInt(10)+1);
						emitter.send(gr);
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				emitter.complete();
			}

		}).start();
		return emitter;
	}

	@GetMapping("/mono-sse")
	public Flux<ServerSentEvent<String>> singleEvent() throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = new JSONObject();
		String timeNow = Instant.now().toString();
		String toParse = "{\"message\":\"mono server event with server sent event ... @ " + timeNow + "\"}";
		JSONObject json = (JSONObject) parser.parse(toParse);
		// log.info(json.toJSONString());

		return Flux.interval(Duration.ofMillis(100))
				.just(ServerSentEvent.<String>builder().event("message").data(json.toJSONString()).id("11").build());
	}

	@GetMapping(path = "/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> streamFlux() {
		return Flux.interval(Duration.ofSeconds(1)).map(sequence -> "Flux - " + LocalTime.now().toString());
	}
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class EventResponse {
	private String message;
}
