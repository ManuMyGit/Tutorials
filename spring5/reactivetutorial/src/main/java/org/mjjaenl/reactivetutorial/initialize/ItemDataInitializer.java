package org.mjjaenl.reactivetutorial.initialize;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.mjjaenl.reactivetutorial.model.Item;
import org.mjjaenl.reactivetutorial.model.ItemCapped;
import org.mjjaenl.reactivetutorial.repository.ItemCappedReactiveRepository;
import org.mjjaenl.reactivetutorial.service.ItemReactiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component
@Profile("dev")
@Slf4j
public class ItemDataInitializer implements CommandLineRunner {
	@Autowired
	private ItemReactiveService itemReactiveService;

	@Autowired
	private ItemCappedReactiveRepository itemCappedReactiveRepository;

	@Autowired
	private MongoOperations mongoOperations;
	
	@Override
	public void run(String... args) {
		initializeData();
		createCappedCollection();
		dataSetUpForCrappedCollection();
	}
	
	private List<Item> createDataSet() {
		return Arrays.asList(new Item(null, "Item 1", new BigDecimal(1)),
				new Item(null, "Item 2", new BigDecimal(2)),
				new Item(null, "Item 3", new BigDecimal(3)),
				new Item(null, "Item 4", new BigDecimal(4.6)));
	}
	
	private void initializeData() {
		itemReactiveService.deleteAll()
			.thenMany(Flux.fromIterable(createDataSet())) // We're processing several elements: Flux<Mono<Item>>
			.flatMap(itemReactiveService::save) // Flux<Items>
			.blockLast();
	}

	private void createCappedCollection() {
		mongoOperations.dropCollection(ItemCapped.class);
		mongoOperations.createCollection(ItemCapped.class, CollectionOptions.empty().maxDocuments(20).size(3000).capped());
	}

	private void dataSetUpForCrappedCollection() {
		Flux<ItemCapped> itemCappedFlux = Flux.interval(Duration.ofSeconds(1))
				.map(i -> new ItemCapped(null, "Random item " + i, BigDecimal.ONE.add(BigDecimal.valueOf(i))));
		itemCappedReactiveRepository.insert(itemCappedFlux)
			.subscribe(itemCapped -> log.info(itemCapped.toString()));
	}
}
