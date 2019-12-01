package org.mjjaenl.reactivetutorial.fluxandmonobackground;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MonoTest {
	@Test
	public void test001ShouldCreateAndSubscribeMonoNoTest() {
		System.out.println("TEST test001ShouldCreateAndSubscribeMonoNoTest ******");
		Mono<String> stringMono = Mono.just("String 1").log();
		stringMono.subscribe(System.out::println);
		stringMono.subscribe((e) -> System.out.println(e));
	}
	
	@Test
	public void test002ShouldCreateAndSubscribeMonoOnCompleteNoTest() {
		System.out.println("\n\nTEST test002ShouldCreateAndSubscribeMonoOnCompleteNoTest ******");
		Mono<String> stringMono = Mono.just("String 1").log();
		
		stringMono.subscribe(System.out::println,
				(e) -> System.err.println(e),
				() -> System.out.println("Stream completed"));
	}
	
	@Test
	public void test003ShouldCreateAndSubscribeMono() {
		System.out.println("\n\nTEST test003ShouldCreateAndSubscribeMono ******");
		Mono<String> stringMono = Mono.just("String 1").log();
		
		//Order is important, elements are retrieving in the order expected
		StepVerifier.create(stringMono)
			.expectNext("String 1")
			.verifyComplete(); //Without this call, there is no test. VerifyComplete is the method to subscribe the flow.
	}
	
	@Test
	public void test004ShouldCreateAndSubscribeMonoWithErrors() {
		System.out.println("\n\nTEST test004ShouldCreateAndSubscribeMonoWithErrors ******");
		
		//Order is important, elements are retrieving in the order expected
		StepVerifier.create(Flux.error(new RuntimeException("Exception")))
			.expectError()
			.verify();
		StepVerifier.create(Flux.error(new RuntimeException("Exception")))
			.expectError(RuntimeException.class)
			.verify();
		StepVerifier.create(Flux.error(new RuntimeException("Exception")))
			.expectErrorMessage("Exception")
			.verify();
	}
	
	@Test
	public void test005ShouldCreateAndSubscribeMonoWithErrorsAndCount() {
		System.out.println("\n\nTEST test005ShouldCreateAndSubscribeMonoWithErrorsAndCount ******");
		Mono<String> stringMono = Mono.just("String 1")
				.log();
		
		//Order is important, elements are retrieving in the order expected
		StepVerifier.create(stringMono)
			.expectNextCount(1)
			.verifyComplete();
	}
}
