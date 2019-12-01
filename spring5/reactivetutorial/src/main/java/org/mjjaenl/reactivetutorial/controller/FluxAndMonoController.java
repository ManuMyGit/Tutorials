package org.mjjaenl.reactivetutorial.controller;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class FluxAndMonoController {
	@SuppressWarnings("unused")
	@Autowired
	private Environment env;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(FluxAndMonoController.class);
	
	@GetMapping("/fluxblock")
	//With this example, the client will wait 4 seconds because it is expecting a JSON
	public Flux<Integer> fluxBlocking() {
		return Flux.just(1, 2, 3, 4)
				.delayElements(Duration.ofSeconds(1))
				.log();
	}
	
	@GetMapping(value = "/fluxStream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	//With this example, the client will get the data 1 by 1
	public Flux<Integer> fluxNonBlocking() {
		return Flux.just(1, 2, 3, 4)
				.delayElements(Duration.ofSeconds(1))
				.log();
	}
	
	@GetMapping("/fluxStream2")
	//Another example to get the data 1 by 1
    public ResponseEntity<Flux<Integer>> fluxNonBlocking2() {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(Flux.just(1, 2, 3, 4)
        				.delayElements(Duration.ofSeconds(1))
        				.log());
    }
	
	@GetMapping("/monoStream")
	//Another example to get the data 1 by 1
    public ResponseEntity<Mono<Integer>> monoNonBlocking() {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(Mono.just(1).log());
    }
	
	@GetMapping(value = "/infiniteStream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	//With this example, the client will get an infinite stream
	public Flux<Long> fluxNonBlockingInfinite() {
		return Flux.interval(Duration.ofSeconds(1))
				.log();
	}
}
