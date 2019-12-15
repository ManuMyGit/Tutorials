package org.mjjaenl.reactivetutorial.initialize;

import org.mjjaenl.reactivetutorial.model.Item;
import org.mjjaenl.reactivetutorial.model.ItemCapped;
import org.mjjaenl.reactivetutorial.repository.ItemCappedReactiveRepository;
import org.mjjaenl.reactivetutorial.repository.ItemReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Component
public final class InitializeData {
    @Autowired
    private ItemReactiveRepository itemReactiveRepository;

    @Autowired
    private ItemCappedReactiveRepository itemCappedReactiveRepository;

    @Autowired
    private MongoOperations mongoOperations;

    List<Item> items = Arrays.asList(new Item(null, "Item 1", new BigDecimal(1)),
            new Item(null, "Item 2", new BigDecimal(2)),
            new Item(null, "Item 3", new BigDecimal(3)),
            new Item("ABC", "Item 4", new BigDecimal(4.6))); //Used for findById

    public void initializeData() {
        itemReactiveRepository.deleteAll()
                .thenMany(Flux.fromIterable(items)) // We're processing several elements: Flux<Mono<Item>>
                .flatMap(itemReactiveRepository::save) // Flux<Items>
                .doOnNext((item) -> {
                    //No action
                }).blockLast(); // This will do the instruction to wait until all elements are saved, not to do in PRO
    }

    public void initializeCappedData() {
        mongoOperations.dropCollection(ItemCapped.class);
        mongoOperations.createCollection(ItemCapped.class, CollectionOptions.empty().maxDocuments(20).size(50000).capped());

        Flux<ItemCapped> itemCappedFlux = Flux.interval(Duration.ofMillis(100))
                .map(i -> new ItemCapped(null, "Random item " + i, BigDecimal.ONE.add(BigDecimal.valueOf(i))))
                .take(5);
        itemCappedReactiveRepository.insert(itemCappedFlux).doOnNext(System.out::println).blockLast();
    }
}
