package org.mjjaenl.reactivetutorial.fluxandmonobackground;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FluxTest {
	@Test
	public void test001ShouldCreateAndSubscribeFluxNoTest() {
		System.out.println("TEST test001ShouldCreateAndSubscribeFluxNoTest ******");
		Flux<String> stringFlux = Flux.just("String 1", "String 2", "String 3").log();
		stringFlux.subscribe(System.out::println);
		stringFlux.subscribe((e) -> System.out.println(e));
	}
	
	@Test
	public void test002ShouldCreateAndSubscribeFluxWithErrorsNoTest() {
		System.out.println("\n\nTEST test002ShouldCreateAndSubscribeFluxWithErrorsNoTest ******");
		Flux<String> stringFlux = Flux.just("String 1", "String 2", "String 3")
					.concatWith(Flux.error(new RuntimeException("Exception")))
					.log();
		
		stringFlux.subscribe(System.out::println,
				(e) -> System.err.println(e));
	}
	
	@Test
	public void test003ShouldCreateAndSubscribeFluxOnCompleteNoTest() {
		System.out.println("\n\nTEST test003ShouldCreateAndSubscribeFluxOnCompleteNoTest ******");
		Flux<String> stringFlux = Flux.just("String 1", "String 2", "String 3").log();
		
		stringFlux.subscribe(System.out::println,
				(e) -> System.err.println(e),
				() -> System.out.println("Stream completed"));
	}
	
	@Test
	public void test004ShouldCreateAndSubscribeFlux() {
		System.out.println("\n\nTEST test004ShouldCreateAndSubscribeFlux ******");
		Flux<String> stringFlux = Flux.just("String 1", "String 2", "String 3").log();
		
		//Order is important, elements are retrieving in the order expected
		StepVerifier.create(stringFlux)
			.expectNext("String 1")
			.expectNext("String 2")
			.expectNext("String 3")
			.verifyComplete(); //Without this call, there is no test. VerifyComplete is the method to subscribe the flow.
	}
	
	@Test
	public void test005ShouldCreateAndSubscribeFluxWithErrors() {
		System.out.println("\n\nTEST test005ShouldCreateAndSubscribeFluxWithErrors ******");
		Flux<String> stringFlux = Flux.just("String 1", "String 2", "String 3")
				.concatWith(Flux.error(new RuntimeException("Exception")))
				.log();
		
		//Order is important, elements are retrieving in the order expected
		StepVerifier.create(stringFlux)
			.expectNext("String 1")
			.expectNext("String 2")
			.expectNext("String 3")
			.expectError()
			.verify();
		StepVerifier.create(stringFlux)
			.expectNext("String 1")
			.expectNext("String 2")
			.expectNext("String 3")
			.expectError(RuntimeException.class)
			.verify();
		StepVerifier.create(stringFlux)
			.expectNext("String 1")
			.expectNext("String 2")
			.expectNext("String 3")
			.expectErrorMessage("Exception")
			.verify();
	}
	
	@Test
	public void test006ShouldCreateAndSubscribeFluxWithErrorsAndCount() {
		System.out.println("\n\nTEST test006ShouldCreateAndSubscribeFluxAndCount ******");
		Flux<String> stringFlux = Flux.just("String 1", "String 2", "String 3")
				.concatWith(Flux.error(new RuntimeException("Exception")))
				.log();
		
		//Order is important, elements are retrieving in the order expected
		StepVerifier.create(stringFlux)
			.expectNextCount(3)
			.expectError(RuntimeException.class)
			.verify();
	}
}
