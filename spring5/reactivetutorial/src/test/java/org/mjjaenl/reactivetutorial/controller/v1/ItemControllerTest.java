package org.mjjaenl.reactivetutorial.controller.v1;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mjjaenl.reactivetutorial.model.Item;
import org.mjjaenl.reactivetutorial.repository.ItemReactiveRepository;
import org.mjjaenl.reactivetutorial.utils.ItemConstants;
import org.mjjaenl.reactivetutorial.utils.MyMediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ItemControllerTest {
	@Autowired
	private WebTestClient webTestClient;
	
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
			.thenMany(Flux.fromIterable(items)) // We're processing several elements: Flux<Mono<Item>>
			.flatMap(itemReactiveRepository::save) // Flux<Items>
			.doOnNext((item) -> {
				//No action
			}).blockLast(); // This will do the instruction to wait until all elements are saved, not to do
								// in PRO
		System.out.println("END settingUp ******");
	}

	@Test
	public void test001GetAllItemsApproach1() {
		System.out.println("\n\nTEST test001GetAllItems ******");
		webTestClient.get().uri(ItemConstants.ITEM_URI_V1)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MyMediaType.APPLICATION_STREAM_JSON_UTF8)
			.expectBodyList(Item.class)
			.hasSize(4);
	}
	
	@Test
	public void test002GetAllItemsApproach2() {
		System.out.println("\n\nTEST test002GetAllItemsApproach2 ******");
		webTestClient.get().uri(ItemConstants.ITEM_URI_V1)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MyMediaType.APPLICATION_STREAM_JSON_UTF8)
			.expectBodyList(Item.class)
			.hasSize(4)
			.consumeWith((response) -> {
				List<Item> listItems = response.getResponseBody();
				listItems.forEach(item -> {
					assertTrue(item.getId() != null);
				});
			});
	}
	
	@Test
	public void test003GetAllItemsApproach3() {
		System.out.println("\n\nTEST test003GetAllItemsApproach3 ******");
		Flux<Item> items = webTestClient.get().uri(ItemConstants.ITEM_URI_V1)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MyMediaType.APPLICATION_STREAM_JSON_UTF8)
			.returnResult(Item.class)
			.getResponseBody();
		StepVerifier.create(items.log())
			.expectSubscription()
			.expectNextCount(4)
			.verifyComplete();
	}
	
	@Test
	public void test004GetByIdApproach1() {
		System.out.println("\n\nTEST test004GetByIdApproach1 ******");
		webTestClient.get().uri(ItemConstants.ITEM_BY_ID_URI_V1, "ABC")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentTypeCompatibleWith(MyMediaType.APPLICATION_JSON_UTF8)
			.expectBody(Item.class)
			.consumeWith(itemResponse -> assertTrue(itemResponse.getResponseBody().getId().equals("ABC")));
	}
	
	@Test
	public void test005GetByIdApproach2() {
		System.out.println("\n\nTEST test005GetByIdApproach2 ******");
		webTestClient.get().uri(ItemConstants.ITEM_BY_ID_URI_V1, "ABC")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentTypeCompatibleWith(MyMediaType.APPLICATION_JSON_UTF8)
			.expectBody()
			.jsonPath("$.id", "ABC");
	}
	
	@Test
	public void test006GetByIdNotFound() {
		System.out.println("\n\nTEST test006GetByIdNotFound ******");
		webTestClient.get().uri(ItemConstants.ITEM_BY_ID_URI_V1, "1234")
			.exchange()
			.expectStatus().isNotFound();
	}
	
	@Test
	public void test007GetByDescription() {
		System.out.println("\n\nTEST test007GetByDescription ******");
		webTestClient.get().uri(ItemConstants.ITEM_URI_V1+"?description=Item 1")
			.exchange()
			.expectStatus().isOk()
			.expectBody(Item.class)
			.consumeWith(itemResponse -> {
				assertTrue(itemResponse.getResponseBody().getDescription().equals("Item 1"));
			});
	}
	
	@Test
	public void test008CreateItem() {
		System.out.println("\n\nTEST test008CreateItem ******");
		Item item = new Item(null, "iPhone X", new BigDecimal(999.99));
		webTestClient.post().uri(ItemConstants.ITEM_URI_V1)
			.contentType(MyMediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(item), Item.class)
			.exchange()
			.expectBody()
			.jsonPath("$.id").isNotEmpty()
			.jsonPath("$.description").isEqualTo("iPhone X")
			.jsonPath("$.price").isEqualTo(new BigDecimal(999.99).setScale(2, RoundingMode.HALF_UP));
	}
	
	@Test
	public void test009CreateInvalidItem() {
		System.out.println("\n\nTEST test009CreateInvalidItem ******");
		Item item = new Item(null, null, new BigDecimal(999.99));
		webTestClient.post().uri(ItemConstants.ITEM_URI_V1)
			.contentType(MyMediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(item), Item.class)
			.exchange()
			.expectStatus().isBadRequest();
	}
	
	@Test
	public void test010DeleteItem() {
		System.out.println("\n\nTEST test010DeleteItem ******");
		webTestClient.delete().uri(ItemConstants.ITEM_BY_ID_URI_V1, "ABC")
			.accept(MyMediaType.APPLICATION_JSON_UTF8)
			.exchange()
			.expectStatus().isNoContent()
			.expectBody(Void.class);
	}
	
	@Test
	public void test011DeleteNonExistingItem() {
		System.out.println("\n\nTEST test011DeleteNonExistingItem ******");
		webTestClient.delete().uri(ItemConstants.ITEM_BY_ID_URI_V1, "1234")
			.accept(MyMediaType.APPLICATION_JSON_UTF8)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody(Void.class);
	}
	
	@Test
	public void test012UpdateItem() {
		System.out.println("\n\nTEST test012UpdateItem ******");
		Item item = new Item(null, "QQQ", new BigDecimal(88.88));
		webTestClient.put().uri(ItemConstants.ITEM_BY_ID_URI_V1, "ABC")
			.contentType(MyMediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(item), Item.class)
			.accept(MyMediaType.APPLICATION_JSON_UTF8)
			.exchange()
			.expectStatus().isOk()
			.expectBody(Item.class)
			.consumeWith(itemResponse -> {
				assertTrue(itemResponse.getResponseBody().getId().equals("ABC"));
				assertTrue(itemResponse.getResponseBody().getDescription().equals("QQQ"));
				assertTrue(itemResponse.getResponseBody().getPrice().equals(new BigDecimal(88.88).setScale(2, RoundingMode.HALF_UP)));
			});
	}
	
	@Test
	public void test013UpdateNonExistingItem() {
		System.out.println("\n\nTEST test013UpdateNonExistingItem ******");
		Item item = new Item(null, "QQQ", new BigDecimal(88.88));
		webTestClient.put().uri(ItemConstants.ITEM_BY_ID_URI_V1, "1234")
			.contentType(MyMediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(item), Item.class)
			.accept(MyMediaType.APPLICATION_JSON_UTF8)
			.accept(MyMediaType.APPLICATION_JSON_UTF8)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody(Void.class);
	}
	
	@Test
	public void test014UpdateNonValidItem() {
		System.out.println("\n\nTEST test014UpdateNonValidItem ******");
		Item item = new Item(null, null, new BigDecimal(88.88));
		webTestClient.put().uri(ItemConstants.ITEM_BY_ID_URI_V1, "ABC")
			.contentType(MyMediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(item), Item.class)
			.accept(MyMediaType.APPLICATION_JSON_UTF8)
			.accept(MyMediaType.APPLICATION_JSON_UTF8)
			.exchange()
			.expectStatus().isBadRequest()
			.expectBody(Void.class);
	}
}
