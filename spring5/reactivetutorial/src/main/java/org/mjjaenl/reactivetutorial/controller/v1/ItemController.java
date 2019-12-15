package org.mjjaenl.reactivetutorial.controller.v1;

import static org.mjjaenl.reactivetutorial.utils.ItemConstants.ITEM_BY_ID_URI_V1;
import static org.mjjaenl.reactivetutorial.utils.ItemConstants.ITEM_URI_V1;

import java.util.Map;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.mjjaenl.reactivetutorial.model.Item;
import org.mjjaenl.reactivetutorial.service.ItemReactiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class ItemController {
	@Autowired
	private ItemReactiveService itemReactiveService;

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
		log.error("RuntimeException caught: ", e);
		if(e instanceof WebExchangeBindException)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}
	
	@GetMapping(value = ITEM_URI_V1, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<Item> getAll(@RequestParam Map<String,String> params) {
		return itemReactiveService.findAll(params);
	}

	@GetMapping(value = ITEM_URI_V1 + "/RuntimeException")
	public Flux<Item> getAllWithRuntimeException(@RequestParam Map<String,String> params) {
		return itemReactiveService.findAll(params).concatWith(Mono.error(new RuntimeException("RuntimeException occurred")));
	}

	@GetMapping(value = ITEM_URI_V1 + "/Exception")
	public Flux<Item> getAllWithException(@RequestParam Map<String,String> params) {
		return itemReactiveService.findAll(params).concatWith(Mono.error(new Exception("Exception occurred")));
	}
	
	@GetMapping(ITEM_BY_ID_URI_V1)
	public Mono<ResponseEntity<Item>> getById(@PathVariable String id) {
		return itemReactiveService.findById(id)
			.map(item -> new ResponseEntity<Item>(item, HttpStatus.OK))
			.onErrorResume(error -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
	}
	
	@PostMapping(ITEM_URI_V1)
	public Mono<ResponseEntity<Item>> create(@RequestBody @Valid Item item) {
		return itemReactiveService.save(item)
			.map(savedItem -> new ResponseEntity<Item>(savedItem, HttpStatus.CREATED));
	}
	
	@DeleteMapping(ITEM_BY_ID_URI_V1)
	public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
		return itemReactiveService.delete(id)
			.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
			.onErrorResume(error -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
	}
	
	@DeleteMapping(ITEM_URI_V1)
	public Mono<ResponseEntity<Void>> deleteAll() {
		return itemReactiveService.deleteAll()
			.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
	}
	
	@PutMapping(ITEM_BY_ID_URI_V1)
	public Mono<ResponseEntity<Item>> update(@PathVariable String id, @RequestBody @Valid Item item) {
		return itemReactiveService.update(id, item)
			.map(itemUpdated -> new ResponseEntity<Item>(itemUpdated, HttpStatus.OK))
			.onErrorResume(error -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
	}
}
