package org.mjjaenl.reactivetutorial.repository.impl;

import org.mjjaenl.reactivetutorial.model.Item;
import org.mjjaenl.reactivetutorial.repository.ItemReactiveRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Example;

import reactor.core.publisher.Flux;

@Repository
public class ItemReactiveRepositoryCustomImpl implements ItemReactiveRepositoryCustom {
	@Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;
	
	@Override
	public Flux<Item> findByDescription(String description) {
		Query query = new Query();
		return reactiveMongoTemplate.find(query.addCriteria(Criteria.where("description").is(description)), Item.class);
	}
	
	@Override
	public Flux<Item> findByDescriptionUsingExample(String description) {
		Item item = new Item();
		item.setDescription(description);
		return reactiveMongoTemplate.find(Query.query(Criteria.byExample(Example.of(item))), Item.class);
	}
}
