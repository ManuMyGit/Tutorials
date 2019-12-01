package org.mjjaenl.reactivetutorial.handler.v1;

import static org.mjjaenl.reactivetutorial.utils.Utils.convertMultiValueMapStringStringToMap;

import javax.validation.Validator;

import org.mjjaenl.reactivetutorial.exception.NotFoundException;
import org.mjjaenl.reactivetutorial.model.Item;
import org.mjjaenl.reactivetutorial.service.ItemReactiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class ItemHandler {
	@Autowired
	private ItemReactiveService itemReactiveService;
	
	@Autowired
	private Validator validator;
	
	public Mono<ServerResponse> getAll(ServerRequest request) {
		return ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_STREAM_JSON)
				.body(itemReactiveService.findAll(convertMultiValueMapStringStringToMap(request.queryParams())), Item.class);
	}
	
	public Mono<ServerResponse> getById(ServerRequest request) {
		Mono<Item> mono = itemReactiveService.findById(request.pathVariable("id"));
		mono.onErrorResume(e -> Mono.empty());
		return ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(mono, Item.class)
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> create(ServerRequest request) {
		Mono<Item> item = request.bodyToMono(Item.class);
		return item.flatMap(newItem -> 
				validator.validate(newItem).isEmpty() ?
					ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(itemReactiveService.save(newItem), Item.class) : 
						ServerResponse.badRequest().build());
	}
	
	public Mono<ServerResponse> delete(ServerRequest request) {
		return ServerResponse
				.ok()
				.body(itemReactiveService.delete(request.pathVariable("id")), Void.class)
				.onErrorResume(NotFoundException.class, e -> ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> deleteAll(ServerRequest request) {
		return ServerResponse
				.ok()
				.body(itemReactiveService.deleteAll(), Void.class);
	}
	
	public Mono<ServerResponse> update(ServerRequest request) {
		Mono<Item> item = request.bodyToMono(Item.class);
		return item.flatMap(newItem -> 
				validator.validate(newItem).isEmpty() ?
					ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(itemReactiveService.update(request.pathVariable("id"), newItem), Item.class)
					.onErrorResume(NotFoundException.class, e -> ServerResponse.notFound().build()) : 
						ServerResponse.badRequest().build());
	}
}
