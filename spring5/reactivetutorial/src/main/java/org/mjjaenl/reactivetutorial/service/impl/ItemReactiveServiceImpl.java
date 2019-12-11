package org.mjjaenl.reactivetutorial.service.impl;

import static org.mjjaenl.reactivetutorial.utils.Utils.isEmpty;

import java.time.Duration;
import java.util.Map;

import org.mjjaenl.reactivetutorial.exception.NotFoundException;
import org.mjjaenl.reactivetutorial.model.Item;
import org.mjjaenl.reactivetutorial.repository.ItemReactiveRepository;
import org.mjjaenl.reactivetutorial.service.ItemReactiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ItemReactiveServiceImpl implements ItemReactiveService {
	@Autowired
	private ItemReactiveRepository itemRepository;
	
	@Override
	public Flux<Item> findAll(Map<String,String> params) {
		if(isEmpty(params))
			return itemRepository.findAll().delayElements(Duration.ofSeconds(2));
		else
			return itemRepository.findByDescription(params.get("description"));
	}

	@Override
	public Mono<Item> findById(String id) {
		return itemRepository.findById(id)
			.switchIfEmpty(Mono.error(new NotFoundException("Item not found")));
	}
	
	@Override
	public Flux<Item> findByDescription(String description) {
		return itemRepository.findByDescription(description);
	}

	@Override
	public Mono<Item> save(Item item) {
		return itemRepository.save(item);
	}

	@Override
	public Mono<Void> delete(String id) {
		return this.findById(id)
			.switchIfEmpty(Mono.error(new NotFoundException("Item not found")))
			.flatMap(item -> itemRepository.deleteById(id));
	}
	
	@Override
	public Mono<Void> deleteAll() {
		return itemRepository.deleteAll();
	}

	@Override
	public Mono<Item> update(String id, Item item) {
		return this.findById(id)
			.switchIfEmpty(Mono.error(new NotFoundException("Item not found")))
			.flatMap(currentItem -> {
				currentItem.setDescription(item.getDescription());
				currentItem.setPrice(item.getPrice());
				return itemRepository.save(currentItem);
			});
	}
}
