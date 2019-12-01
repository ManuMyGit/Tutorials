package org.mjjaenl.reactivetutorial.repository;

import org.mjjaenl.reactivetutorial.model.Item;

import reactor.core.publisher.Flux;

public interface ItemReactiveRepositoryCustom {
	public Flux<Item> findByDescription(String description);
	public Flux<Item> findByDescriptionUsingExample(String description);
}
