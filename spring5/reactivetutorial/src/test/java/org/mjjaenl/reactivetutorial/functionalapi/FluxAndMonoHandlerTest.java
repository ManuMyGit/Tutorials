package org.mjjaenl.reactivetutorial.functionalapi;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mjjaenl.reactivetutorial.utils.MyMediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest //We cannot use @WebFluxTest because we're testing a class annotated as @Component, not as @RestController. If you use @WebFluxTest, you'll get a 404 in the tests because the routes are not scanned.s
@AutoConfigureWebTestClient //This annotation is used to configure webTestClient. It is configured by @WebFluxTest but, since we're not using it, if you don't use this annotation this dependency won't be found.
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("local")
public class FluxAndMonoHandlerTest {
	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	public void test001FunctionalFluxTest() {
		System.out.println("TEST test001FunctionalFluxTest ******");
		Flux<Integer> flux = webTestClient.get().uri("/functional/flux")
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
	public void test002FunctionalMonoTest() {
		System.out.println("\n\nTEST test002FunctionalMonoTest ******");
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
