package org.mjjaenl.reactivetutorial.service;

import java.util.Map;

import org.mjjaenl.reactivetutorial.model.Item;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemReactiveService {
	public Flux<Item> findAll(Map<String,String> params);
	public Mono<Item> findById(String id);
	public Flux<Item> findByDescription(String description);
	public Mono<Item> save(Item item);
	public Mono<Void> delete(String id);
	public Mono<Void> deleteAll();
	public Mono<Item> update(String id, Item item);
}
