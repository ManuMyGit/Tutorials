package org.mjjaenl.reactivetutorial.controller;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mjjaenl.reactivetutorial.utils.MyMediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient //It is responsible of creating the instance of WebTestClient
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FluxAndMonoControllerTest {
	@Autowired
	WebTestClient webTestClient; //Introduced in Spring 5. Equivalent to TestRestTemplate for blocking REST API
	
	@Test
	public void test001FluxBlockingTest() {
		System.out.println("TEST test001FluxBlockingTest ******");
		Flux<Integer> flux = webTestClient.get().uri("/fluxblock")
			.accept(MyMediaType.APPLICATION_JSON_UTF8)
			.exchange() //This is the one which actually makes the call to the endpoint
			.expectStatus().isOk()
			.returnResult(Integer.class)
			.getResponseBody();
		
		StepVerifier.create(flux)
			.expectSubscription()
			.expectNext(1, 2, 3, 4)
			.verifyComplete();
	}
	
	@Test
	public void test002FluxNonBlockingApproach1Test() {
		System.out.println("\n\nTEST test002FluxNonBlockingApproach1Test ******");
		webTestClient.get().uri("/fluxStream")
			.accept(MyMediaType.APPLICATION_STREAM_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MyMediaType.APPLICATION_STREAM_JSON_UTF8)
			.expectBodyList(Integer.class)
			.hasSize(4);
	}
	
	@Test
	public void test003FluxNonBlockingApproach2Test() {
		System.out.println("\n\nTEST test003FluxNonBlockingApproach2Test ******");
		List<Integer> expectedList = Arrays.asList(1, 2, 3, 4);
		EntityExchangeResult<List<Integer>> entityExchangeResult = webTestClient.get().uri("/fluxStream2")
			.accept(MyMediaType.APPLICATION_STREAM_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MyMediaType.APPLICATION_STREAM_JSON)
			.expectBodyList(Integer.class)
			.returnResult();
		assertEquals(expectedList, entityExchangeResult.getResponseBody());
	}
	
	@Test
	public void test004FluxNonBlockingApproach3Test() {
		System.out.println("\n\nTEST test004FluxNonBlockingApproach3Test ******");
		List<Integer> expectedList = Arrays.asList(1, 2, 3, 4);
		webTestClient.get().uri("/fluxStream2")
			.accept(MyMediaType.APPLICATION_STREAM_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MyMediaType.APPLICATION_STREAM_JSON)
			.expectBodyList(Integer.class)
			.consumeWith((response) -> {
				assertEquals(expectedList, response.getResponseBody());
			});
	}
	
	@Test
	public void test005FluxInfiniteStreamTest() {
		System.out.println("\n\nTEST test005FluxInfiniteStreamTest ******");
		Flux<Long> flux = webTestClient.get().uri("/infiniteStream")
			.accept(MyMediaType.APPLICATION_STREAM_JSON)
			.exchange() //This is the one which actually makes the call to the endpoint
			.expectStatus().isOk()
			.returnResult(Long.class)
			.getResponseBody();
		
		StepVerifier.create(flux)
			.expectSubscription()
			.expectNext(0L)
			.expectNext(1L)
			.expectNext(2L)
			.expectNext(3L)
			.thenCancel()
			.verify();
	}
	
	@Test
	public void test006MonoNonBlockingTest() {
		System.out.println("\n\nTEST test006MonoNonBlockingTest ******");
		webTestClient.get().uri("/monoStream")
			.accept(MyMediaType.APPLICATION_STREAM_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MyMediaType.APPLICATION_STREAM_JSON)
			.expectBody(Integer.class)
			.consumeWith((response) -> {
				assertEquals(new Integer(1), response.getResponseBody());
			});
	}
}
