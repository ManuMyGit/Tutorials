package org.mjjaenl.reactivetutorial.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mjjaenl.reactivetutorial.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ItemReactiveServiceTest {
	@Autowired
	private ItemReactiveService itemReactiveService;
	
	List<Item> items = Arrays.asList(new Item(null, "Item 1", new BigDecimal(1)),
		new Item(null, "Item 2", new BigDecimal(2)),
		new Item(null, "Item 3", new BigDecimal(3)),
		new Item("ABC", "Item 4", new BigDecimal(4.6)));
	
	@Before
	public void setUp() throws Exception {
		System.out.println("\n\nINIT settingUp ******");
		itemReactiveService.deleteAll()
			.thenMany(Flux.fromIterable(items)) //We're processing several elements: Flux<Mono<Item>>
			.flatMap(itemReactiveService::save) //Flux<Items>
			.doOnNext((item) -> {
				System.out.println(item);
			})
			.blockLast(); //This will do the instruction to wait until all elements are saved
		System.out.println("END settingUp ******");
	}

	@Test
	public void test001GetAllItems() {
		System.out.println("\n\nINIT TEST test001GetAllItems ******");
		StepVerifier.create(itemReactiveService.findAll(null))
			.expectSubscription()
			.expectNextCount(4)
			.verifyComplete();
		System.out.println("END TEST test001GetAllItems ******");
	}
	
	@Test
	public void test002GetItemById() {
		System.out.println("\n\nINIT TEST test002GetItemById ******");
		StepVerifier.create(itemReactiveService.findById("ABC"))
			.expectSubscription()
			.expectNextMatches((item) -> item.getDescription().equals(items.get(3).getDescription()))
			.verifyComplete();
		System.out.println("END TEST test002GetItemById ******");
	}
	
	@Test
	public void test003GetItemByDescription() {
		System.out.println("\n\nINIT TEST test003GetItemByDescription ******");
		StepVerifier.create(itemReactiveService.findByDescription("Item 4"))
			.expectSubscription()
			.expectNextMatches((item) -> item.getDescription().equals(items.get(3).getDescription()))
			.verifyComplete();
		System.out.println("END TEST test003GetItemByDescription ******");
	}
	
	@Test
	public void test004SaveItem() {
		System.out.println("\n\nTEST test004saveItem ******");
		Item item = new Item(null, "Item 5", new BigDecimal(10.99));
		StepVerifier.create(itemReactiveService.save(item).log("savedItem : "))
			.expectSubscription()
			.expectNextMatches((itemFromDatabase) -> item.getDescription().equals(itemFromDatabase.getDescription()) && itemFromDatabase.getId() != null)
			.verifyComplete();
		System.out.println("END TEST test004saveItem ******");
	}
	
	@Test
	public void test005UpdateItem() {
		System.out.println("\n\nTEST test005UpdateItem ******");
		Flux<Item> updatedItem = itemReactiveService.findByDescription("Item 4")
				.map(item -> {
					item.setPrice(new BigDecimal(22.55));
					return item;
				})
				.flatMap(item -> {
					return itemReactiveService.save(item);
				});
		StepVerifier.create(updatedItem)
			.expectSubscription()
			.expectNextMatches((itemFromDatabase) -> new BigDecimal(22.55).equals(itemFromDatabase.getPrice()))
			.verifyComplete();
		System.out.println("END TEST test005UpdateItem ******");
	}
	
	@Test
	public void test006DeleteItem() {
		System.out.println("\n\nTEST test006DeleteItem ******");
		Mono<Void> deletedItem = itemReactiveService.delete("ABC");
		StepVerifier.create(deletedItem)
			.expectSubscription()
			.verifyComplete();
		StepVerifier.create(itemReactiveService.findAll(null))
			.expectNextCount(3)
			.verifyComplete();
		System.out.println("END TEST test006DeleteItem ******");
	}
}
