package org.mjjaenl.reactivetutorial.handler.v1;

import static org.mjjaenl.reactivetutorial.utils.Utils.convertMultiValueMapStringStringToMap;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

import javax.validation.Validator;

import org.mjjaenl.reactivetutorial.exception.BadRequestException;
import org.mjjaenl.reactivetutorial.exception.NotFoundException;
import org.mjjaenl.reactivetutorial.model.Item;
import org.mjjaenl.reactivetutorial.service.ItemReactiveService;
import org.mjjaenl.reactivetutorial.utils.MyMediaType;
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
				.contentType(MyMediaType.APPLICATION_STREAM_JSON_UTF8)
				.body(fromPublisher(itemReactiveService.findAll(convertMultiValueMapStringStringToMap(request.queryParams())), Item.class));
		/*What's the difference???? return ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_STREAM_JSON)
				.body(itemReactiveService.findAll(convertMultiValueMapStringStringToMap(request.queryParams())), Item.class);*/
	}
	
	public Mono<ServerResponse> getById(ServerRequest request) {
		Mono<Item> item = itemReactiveService.findById(request.pathVariable("id"));
		return item
			.flatMap(currentItem -> ServerResponse
										.ok()
										.contentType(MediaType.APPLICATION_JSON)
										.body(fromPublisher(item, Item.class)))
			.onErrorResume(error -> ServerResponse.notFound().build());
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
		Mono<Void> mono = itemReactiveService.delete(request.pathVariable("id"));
		return mono
			.then(ServerResponse.noContent().build())
			.onErrorResume(error -> ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> deleteAll(ServerRequest request) {
		Mono<Void> mono = itemReactiveService.deleteAll();
		return mono
			.then(ServerResponse.noContent().build());
	}
	
	public Mono<ServerResponse> update(ServerRequest request) {
		Mono<Item> updatedItem = request.bodyToMono(Item.class).flatMap(newItem -> validator.validate(newItem).isEmpty() ?
						itemReactiveService.update(request.pathVariable("id"), newItem) : Mono.error(new BadRequestException("Wrong")));
		return updatedItem
			.flatMap(newItem -> 
						ServerResponse
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(itemReactiveService.update(request.pathVariable("id"), newItem), Item.class))
			.onErrorResume(NotFoundException.class, e -> ServerResponse.notFound().build())
			.onErrorResume(BadRequestException.class, e -> ServerResponse.badRequest().build());
	}
}
