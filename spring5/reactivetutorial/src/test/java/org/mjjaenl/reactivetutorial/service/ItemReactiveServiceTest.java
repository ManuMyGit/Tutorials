package org.mjjaenl.reactivetutorial.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mjjaenl.reactivetutorial.exception.NotFoundException;
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
	public void test003GetItemByNonExistingId() {
		System.out.println("\n\nINIT TEST test003GetItemByNonExistingId ******");
		StepVerifier.create(itemReactiveService.findById("1234"))
			.expectSubscription()
			.expectError(NotFoundException.class)
			.verify();
		System.out.println("END TEST test003GetItemByNonExistingId ******");
	}
	
	@Test
	public void test004GetItemByDescription() {
		System.out.println("\n\nINIT TEST test004GetItemByDescription ******");
		StepVerifier.create(itemReactiveService.findByDescription("Item 4"))
			.expectSubscription()
			.expectNextMatches((item) -> item.getDescription().equals(items.get(3).getDescription()))
			.verifyComplete();
		System.out.println("END TEST test004GetItemByDescription ******");
	}
	
	@Test
	public void test005SaveItem() {
		System.out.println("\n\nTEST test005SaveItem ******");
		Item item = new Item(null, "Item 5", new BigDecimal(10.99));
		StepVerifier.create(itemReactiveService.save(item).log("savedItem : "))
			.expectSubscription()
			.expectNextMatches((itemFromDatabase) -> item.getDescription().equals(itemFromDatabase.getDescription()) && itemFromDatabase.getId() != null)
			.verifyComplete();
		System.out.println("END TEST test005SaveItem ******");
	}
	
	@Test
	public void test006UpdateItem() {
		System.out.println("\n\nTEST test006UpdateItem ******");
		Flux<Item> updatedItem = itemReactiveService.findByDescription("Item 4")
				.map(item -> {
					item.setPrice(new BigDecimal(22.55));
					return item;
				})
				.flatMap(item -> {
					return itemReactiveService.update(item.getId(), item);
				});
		StepVerifier.create(updatedItem)
			.expectSubscription()
			.expectNextMatches((itemFromDatabase) -> new BigDecimal(22.55).equals(itemFromDatabase.getPrice()))
			.verifyComplete();
		System.out.println("END TEST test006UpdateItem ******");
	}
	
	@Test
	public void test007UpdateNonExistingItem() {
		System.out.println("\n\nTEST test007UpdateNonExistingItem ******");
		Mono<Item> updatedItem = itemReactiveService.update("12345", null);
		StepVerifier.create(updatedItem)
			.expectError(NotFoundException.class)
			.verify();
		System.out.println("END TEST test007UpdateNonExistingItem ******");
	}
	
	@Test
	public void test008DeleteItem() {
		System.out.println("\n\nTEST test008DeleteItem ******");
		Mono<Void> deletedItem = itemReactiveService.delete("ABC");
		StepVerifier.create(deletedItem)
			.expectSubscription()
			.verifyComplete();
		StepVerifier.create(itemReactiveService.findAll(null))
			.expectNextCount(3)
			.verifyComplete();
		System.out.println("END TEST test008DeleteItem ******");
	}
	
	@Test
	public void test009DeleteNonExistingItem() {
		System.out.println("\n\nTEST test009DeleteNonExistingItem ******");
		Mono<Void> deletedItem = itemReactiveService.delete("12345");
		StepVerifier.create(deletedItem)
			.expectError(NotFoundException.class)
			.verify();
		System.out.println("END TEST test009DeleteNonExistingItem ******");
	}
}
