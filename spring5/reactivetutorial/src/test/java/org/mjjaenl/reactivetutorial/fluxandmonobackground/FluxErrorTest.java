package org.mjjaenl.reactivetutorial.fluxandmonobackground;

import java.time.Duration;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mjjaenl.reactivetutorial.exception.CustomException;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FluxErrorTest {
	@Test
	public void test001FluxErrorHandling() {
		System.out.println("TEST test001FluxErrorHandling ******");
		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Exception")))
				.concatWith(Flux.just("D"))
				.onErrorResume((e) -> {
					System.out.println("Exception is" + e);
					return Flux.just("default", "default1");
				});
		
		StepVerifier.create(stringFlux.log())
			.expectSubscription()
			.expectNext("A", "B", "C")
			.expectNext("default", "default1")
			.verifyComplete();
	}
	
	@Test
	public void test002FluxErrorHandlingErrorReturn() {
		System.out.println("\n\nTEST test002FluxErrorHandlingErrorReturn ******");
		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Exception")))
				.concatWith(Flux.just("D"))
				.onErrorReturn("default");
		
		StepVerifier.create(stringFlux.log())
			.expectSubscription()
			.expectNext("A", "B", "C")
			.expectNext("default")
			.verifyComplete();
	}
	
	@Test
	public void test003FluxErrorHandlingErrorMap() {
		System.out.println("\n\nTEST test003FluxErrorHandlingErrorMap ******");
		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Exception")))
				.concatWith(Flux.just("D"))
				.onErrorMap((e) -> {
					return new CustomException(e);
				});
		
		StepVerifier.create(stringFlux.log())
			.expectSubscription()
			.expectNext("A", "B", "C")
			.expectError(CustomException.class)
			.verify();
	}
	
	@Test
	public void test004FluxErrorHandlingErrorMapWithRetry() {
		System.out.println("\n\nTEST test004FluxErrorHandlingErrorMapWithRetry ******");
		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Exception")))
				.concatWith(Flux.just("D"))
				.onErrorMap((e) -> {
					return new CustomException(e);
				})
				.retry(2);
		
		StepVerifier.create(stringFlux.log())
			.expectSubscription()
			.expectNext("A", "B", "C")
			.expectNext("A", "B", "C")
			.expectNext("A", "B", "C")
			.expectError(CustomException.class)
			.verify();
	}
	
	@Test
	public void test005FluxErrorHandlingErrorMapWithRetryBackoff() {
		System.out.println("\n\nTEST test005FluxErrorHandlingErrorMapWithRetryBackoff ******");
		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Exception")))
				.concatWith(Flux.just("D"))
				.onErrorMap((e) -> {
					return new CustomException(e);
				})
				.retryBackoff(2, Duration.ofSeconds(5));
		
		StepVerifier.create(stringFlux.log())
			.expectSubscription()
			.expectNext("A", "B", "C")
			.expectNext("A", "B", "C")
			.expectNext("A", "B", "C")
			.expectError(IllegalStateException.class)
			.verify();
	}
}
