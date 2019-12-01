package org.mjjaenl.reactivetutorial.repository;

import java.math.BigDecimal;

import org.mjjaenl.reactivetutorial.model.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface ItemReactiveRepository extends ReactiveMongoRepository<Item, String>, ItemReactiveRepositoryCustom {
	public Flux<Item> findByPrice(BigDecimal price);
}
