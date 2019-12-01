package org.mjjaenl.reactivetutorial.initialize;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.mjjaenl.reactivetutorial.model.Item;
import org.mjjaenl.reactivetutorial.service.ItemReactiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component
@Profile("dev")
public class ItemDataInitializer implements CommandLineRunner {
	@Autowired
	private ItemReactiveService itemReactiveService;
	
	@Override
	public void run(String... args) throws Exception {
		initializeData();
	}
	
	public List<Item> createDataSet() {
		return Arrays.asList(new Item(null, "Item 1", new BigDecimal(1)),
				new Item(null, "Item 2", new BigDecimal(2)),
				new Item(null, "Item 3", new BigDecimal(3)),
				new Item(null, "Item 4", new BigDecimal(4.6)));
	}
	
	public void initializeData() {
		itemReactiveService.deleteAll()
			.thenMany(Flux.fromIterable(createDataSet())) // We're processing several elements: Flux<Mono<Item>>
			.flatMap(itemReactiveService::save) // Flux<Items>
			.blockLast();
	}
}
