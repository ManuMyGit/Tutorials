package org.mjjaenl.reactivetutorial.handler;

import java.time.Duration;

import org.mjjaenl.reactivetutorial.exception.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class FluxAndMonoHandler {
	public Mono<ServerResponse> flux(ServerRequest request) {
		Flux<Integer> flux = Flux.just(1, 2, 3, 4).delayElements(Duration.ofSeconds(1));
		return ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_STREAM_JSON)
				.body(flux.log(), Integer.class)
				.onErrorResume(NotFoundException.class, e -> ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> mono(ServerRequest request) {
		Mono<Integer> mono = Mono.just(1);
		return ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_STREAM_JSON)
				.body(mono.log(), Integer.class);
	}
}
