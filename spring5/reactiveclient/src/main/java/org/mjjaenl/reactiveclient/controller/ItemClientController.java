package org.mjjaenl.reactiveclient.controller;

import static org.mjjaenl.reactiveclient.utils.Constants.CLIENT_ITEM_BY_ID_URI_V1_EXCHANGE;
import static org.mjjaenl.reactiveclient.utils.Constants.CLIENT_ITEM_BY_ID_URI_V1_RETRIEVE;
import static org.mjjaenl.reactiveclient.utils.Constants.CLIENT_ITEM_URI_V1_EXCHANGE;
import static org.mjjaenl.reactiveclient.utils.Constants.CLIENT_ITEM_URI_V1_RETRIEVE;
import static org.mjjaenl.reactiveclient.utils.Constants.SERVER_ITEM_BY_ID_URI_V1;
import static org.mjjaenl.reactiveclient.utils.Constants.SERVER_ITEM_URI_V1;

import lombok.extern.slf4j.Slf4j;
import org.mjjaenl.reactiveclient.domain.Item;
import org.mjjaenl.reactiveclient.utils.DataEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class ItemClientController {
	@Autowired
	private DataEnvironment env;

	WebClient webClient = WebClient.create("http://localhost:8080");
	
	@GetMapping(value = CLIENT_ITEM_URI_V1_RETRIEVE, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Item> getUsingRetrieve() {
		return webClient.get().uri(SERVER_ITEM_URI_V1)
			.retrieve() //Retrieve gives you the body of the response directly
			.bodyToFlux(Item.class)
			.log("Items in client using retrieve");
	}
	
	@GetMapping(CLIENT_ITEM_URI_V1_EXCHANGE + "/RuntimeException")
	public Flux<Item> getUsingExchange() {
		return webClient.get().uri(SERVER_ITEM_URI_V1 + "/RuntimeException")
			.exchange() //Exchange gives you the access to ClientResponse object
			.flatMapMany(clientResponse -> {
				if(clientResponse.statusCode().is5xxServerError()) {
					return clientResponse.bodyToMono(String.class)
						.flatMap(errorMessage -> {
							log.error("The error message is " + errorMessage);
							throw new RuntimeException(errorMessage);
						});
				} else {
					return clientResponse.bodyToMono(Item.class);
				}
			});
	}

	@GetMapping(CLIENT_ITEM_URI_V1_RETRIEVE + "/RuntimeException")
	public Flux<Item> getWithRuntimeErrorUsingRetrieve() {
		return webClient.get().uri(SERVER_ITEM_URI_V1 + "/RuntimeException")
				.retrieve() //Retrieve gives you the body of the response directly
				.onStatus(HttpStatus::is5xxServerError, clientResponse -> {
					Mono<String> errorMono = clientResponse.bodyToMono(String.class);
					return errorMono.flatMap(errorMessage -> {
						log.error("The error message is " + errorMessage);
						throw new RuntimeException(errorMessage);
					});
				})
				.bodyToFlux(Item.class);
	}

	@GetMapping(value = CLIENT_ITEM_URI_V1_EXCHANGE, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Item> getWithRuntimeErrorUsingExchange() {
		return webClient.get().uri(SERVER_ITEM_URI_V1)
				.exchange() //Exchange gives you the access to ClientResponse object
				.flatMapMany(response -> response.bodyToFlux(Item.class))
				.log("Items in client using exchange");
	}
	
	@GetMapping(CLIENT_ITEM_BY_ID_URI_V1_RETRIEVE)
	public Mono<Item> getByIdUsingRetrieve(@PathVariable String id) {
		return webClient.get().uri(SERVER_ITEM_BY_ID_URI_V1, id)
			.retrieve()
			.bodyToMono(Item.class)
			.log("Item by id in client using retrieve");
	}
	
	@GetMapping(CLIENT_ITEM_BY_ID_URI_V1_EXCHANGE)
	public Mono<Item> getByIdUsingExchange(@PathVariable String id) {
		return webClient.get().uri(SERVER_ITEM_BY_ID_URI_V1, id)
			.exchange()
			.flatMap(item -> item.bodyToMono(Item.class))
			.log("Item by id in client using exchange");
	}
	
	@PostMapping(CLIENT_ITEM_URI_V1_RETRIEVE)
	public Mono<Item> postUsingRetrieve(@RequestBody Item item) {
		Mono<Item> itemMono = Mono.just(item);
		return webClient.post().uri(SERVER_ITEM_URI_V1)
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(itemMono, Item.class)
			.retrieve()
			.bodyToMono(Item.class)
			.log("Create item using retrieve");
	}
	
	@PostMapping(CLIENT_ITEM_URI_V1_EXCHANGE)
	public Mono<Item> postUsingExchange(@RequestBody Item item) {
		Mono<Item> itemMono = Mono.just(item);
		return webClient.post().uri(SERVER_ITEM_URI_V1)
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(itemMono, Item.class)
			.exchange()
			.flatMap(itemCreated -> itemCreated.bodyToMono(Item.class))
			.log("Create item using exchange");
	}
	
	@PutMapping(CLIENT_ITEM_BY_ID_URI_V1_RETRIEVE)
	public Mono<Item> putUsingRetrieve(@PathVariable String id, @RequestBody Item item) {
		Mono<Item> itemMono = Mono.just(item);
		return webClient.put().uri(SERVER_ITEM_BY_ID_URI_V1, id)
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(itemMono, Item.class)
			.retrieve()
			.bodyToMono(Item.class)
			.log("Update item using retrieve");
	}
	
	@PutMapping(CLIENT_ITEM_BY_ID_URI_V1_EXCHANGE)
	public Mono<Item> putUsingExchange(@PathVariable String id, @RequestBody Item item) {
		Mono<Item> itemMono = Mono.just(item);
		return webClient.put().uri(SERVER_ITEM_BY_ID_URI_V1, id)
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(itemMono, Item.class)
			.exchange()
			.flatMap(itemCreated -> itemCreated.bodyToMono(Item.class))
			.log("Update item using exchange");
	}
	
	@DeleteMapping(CLIENT_ITEM_BY_ID_URI_V1_RETRIEVE)
	public Mono<Void> deleteUsingRetrieve(@PathVariable String id) {
		return webClient.delete().uri(SERVER_ITEM_BY_ID_URI_V1, id)
			.retrieve()
			.bodyToMono(Void.class)
			.log("Update item using retrieve");
	}
	
	@DeleteMapping(CLIENT_ITEM_BY_ID_URI_V1_EXCHANGE)
	public Mono<Void> deleteUsingExchange(@PathVariable String id) {
		return webClient.delete().uri(SERVER_ITEM_BY_ID_URI_V1, id)
			.exchange()
			.flatMap(itemCreated -> itemCreated.bodyToMono(Void.class))
			.log("Update item using exchange");
	}
}
