package org.mjjaenl.reactivetutorial.router.v1;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.mjjaenl.reactivetutorial.utils.ItemConstants.ITEM_BY_ID_URI_V1_FUNCTIONAL;
import static org.mjjaenl.reactivetutorial.utils.ItemConstants.ITEM_URI_V1_FUNCTIONAL;

import org.mjjaenl.reactivetutorial.handler.v1.ItemHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ItemRouter {
	@Bean
	public RouterFunction<ServerResponse> getAll(ItemHandler itemHandler) {
		return route(GET(ITEM_URI_V1_FUNCTIONAL)
			.and(accept(MediaType.APPLICATION_JSON)), itemHandler::getAll);
	}
	
	@Bean
	public RouterFunction<ServerResponse> getById(ItemHandler itemHandler) {
		return route(GET(ITEM_BY_ID_URI_V1_FUNCTIONAL)
			.and(accept(MediaType.APPLICATION_JSON)), itemHandler::getById);
	}
	
	@Bean
	public RouterFunction<ServerResponse> create(ItemHandler itemHandler) {
		return route(POST(ITEM_URI_V1_FUNCTIONAL)
			.and(accept(MediaType.APPLICATION_JSON)), itemHandler::create);
	}
	
	@Bean
	public RouterFunction<ServerResponse> delete(ItemHandler itemHandler) {
		return route(DELETE(ITEM_BY_ID_URI_V1_FUNCTIONAL)
			.and(accept(MediaType.APPLICATION_JSON)), itemHandler::delete);
	}
	
	@Bean
	public RouterFunction<ServerResponse> deleteAll(ItemHandler itemHandler) {
		return route(DELETE(ITEM_URI_V1_FUNCTIONAL)
			.and(accept(MediaType.APPLICATION_JSON)), itemHandler::deleteAll);
	}
	
	@Bean
	public RouterFunction<ServerResponse> update(ItemHandler itemHandler) {
		return route(PUT(ITEM_BY_ID_URI_V1_FUNCTIONAL)
			.and(accept(MediaType.APPLICATION_JSON)), itemHandler::update);
	}
}
