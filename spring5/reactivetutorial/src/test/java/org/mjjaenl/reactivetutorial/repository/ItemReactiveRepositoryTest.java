package org.mjjaenl.reactivetutorial.repository;

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
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ItemReactiveRepositoryTest {
	@Autowired
	private ItemReactiveRepository itemReactiveRepository;
	
	List<Item> items = Arrays.asList(new Item(null, "Item 1", new BigDecimal(1)),
			new Item(null, "Item 2", new BigDecimal(2)),
			new Item(null, "Item 3", new BigDecimal(3)),
			new Item("ABC", "Item 4", new BigDecimal(4.6))); //Used for findById
	
	@Before
	public void setUp() {
		System.out.println("\n\nINIT settingUp ******");
		itemReactiveRepository.deleteAll()
			.thenMany(Flux.fromIterable(items)) //We're processing several elements: Flux<Mono<Item>>
			.flatMap(itemReactiveRepository::save) //Flux<Items>
			.doOnNext((item) -> {
				System.out.println(item);
			})
			.blockLast(); //This will do the instruction to wait until all elements are saved
		System.out.println("END settingUp ******");
	}
	
	@Test
	public void test001GetAllItems() {
		System.out.println("\n\nINIT TEST test001GetAllItems ******");
		StepVerifier.create(itemReactiveRepository.findAll())
			.expectSubscription()
			.expectNextCount(4)
			.verifyComplete();
		System.out.println("END TEST test001GetAllItems ******");
	}
	
	@Test
	public void test002GetItemById() {
		System.out.println("\n\nINIT TEST test002GetItemById ******");
		StepVerifier.create(itemReactiveRepository.findById("ABC"))
			.expectSubscription()
			.expectNextMatches((item) -> item.getDescription().equals(items.get(3).getDescription()))
			.verifyComplete();
		System.out.println("END TEST test002GetItemById ******");
	}
	
	@Test
	public void test003GetItemByDescriptionApproach1() {
		System.out.println("\n\nINIT TEST test003GetItemByDescriptionApproach1 ******");
		StepVerifier.create(itemReactiveRepository.findByDescription("Item 4"))
			.expectSubscription()
			.expectNextMatches((item) -> item.getDescription().equals(items.get(3).getDescription()))
			.verifyComplete();
		System.out.println("END TEST test003GetItemByDescriptionApproach1 ******");
	}
	
	@Test
	public void test004GetItemByDescriptionApproach2() {
		System.out.println("\n\nINIT TEST test004GetItemByDescriptionApproach2 ******");
		StepVerifier.create(itemReactiveRepository.findByDescriptionUsingExample("Item 4"))
			.expectSubscription()
			.expectNextMatches((item) -> item.getDescription().equals(items.get(3).getDescription()))
			.verifyComplete();
		System.out.println("END TEST test004GetItemByDescriptionApproach2 ******");
	}
	
	@Test
	public void test005GetItemByPrice() {
		System.out.println("\n\nINIT TEST test005GetItemByPrice ******");
		StepVerifier.create(itemReactiveRepository.findByPrice(new BigDecimal(1)))
			.expectSubscription()
			.expectNextMatches((item) -> item.getPrice().equals(items.get(0).getPrice()))
			.verifyComplete();
		System.out.println("END TEST test005GetItemByPrice ******");
	}
	
	@Test
	public void test006SaveItem() {
		System.out.println("\n\nTEST test006SaveItem ******");
		Item item = new Item(null, "Item 5", new BigDecimal(10.99));
		StepVerifier.create(itemReactiveRepository.save(item).log("savedItem : "))
			.expectSubscription()
			.expectNextMatches((itemFromDatabase) -> item.getDescription().equals(itemFromDatabase.getDescription()) && itemFromDatabase.getId() != null)
			.verifyComplete();
		System.out.println("END TEST test006SaveItem ******");
	}
	
	@Test
	public void test007UpdateItem() {
		System.out.println("\n\nTEST test007UpdateItem ******");
		Flux<Item> updatedItem = itemReactiveRepository.findByDescription("Item 4")
				.map(item -> {
					item.setPrice(new BigDecimal(22.55));
					return item;
				})
				.flatMap(item -> {
					return itemReactiveRepository.save(item);
				});
		StepVerifier.create(updatedItem)
			.expectSubscription()
			.expectNextMatches((itemFromDatabase) -> new BigDecimal(22.55).equals(itemFromDatabase.getPrice()))
			.verifyComplete();
		System.out.println("END TEST test007UpdateItem ******");
	}
	
	@Test
	public void test008DeleteItem() {
		System.out.println("\n\nTEST test008DeleteItem ******");
		Mono<Void> deletedItem = itemReactiveRepository.findById("ABC")
			.map(Item::getId) //Map method is normally used to transform from one type to another type
			.flatMap((id) -> {
				return itemReactiveRepository.deleteById(id);
			});
		StepVerifier.create(deletedItem)
			.expectSubscription()
			.verifyComplete();
		StepVerifier.create(itemReactiveRepository.findAll())
			.expectNextCount(3)
			.verifyComplete();
		System.out.println("END TEST test008DeleteItem ******");
	}
}
