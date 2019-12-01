package org.mjjaenl.reactivetutorial.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.mjjaenl.reactivetutorial.handler.FluxAndMonoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterFunctionConfig {
	@Bean
	public RouterFunction<ServerResponse> functionalResponseFlux(FluxAndMonoHandler fluxHandler) {
		return route(GET("/functional/flux")
			.and(accept(MediaType.APPLICATION_JSON)), fluxHandler::flux);
	}
	
	@Bean
	public RouterFunction<ServerResponse> functionalResponseMono(FluxAndMonoHandler fluxHandler) {
		return route(GET("/functional/mono")
			.and(accept(MediaType.APPLICATION_JSON)), fluxHandler::mono);
	}
}
