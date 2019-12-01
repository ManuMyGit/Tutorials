package org.mjjaenl.reactivetutorial.fluxandmonobackground;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BackPressureTest {
	/*
	 * Basically, back pressure allows the subscriber to control the flow from Publisher.
	 * Subscriber is able to tell the publisher how many data he wants and when to cancel the flow.
	 */
	/*
	 * Without backPressure
	 * Subscriber -----> getAllItems()      -----> Publisher
	 * Subscriber -----> subscribe          -----> Publisher
	 * Subscriber <----- subscription       <----- Publisher
	 * Subscriber -----> request(unbounded) -----> Publisher
	 * Subscriber <----- onNext(item)       <----- Publisher
	 * Subscriber <----- onNext(N-item)     <----- Publisher
	 * Subscriber <----- onComplete()       <----- Publisher
	 */
	/*
	 * With backPressure
	 * Subscriber -----> getAllItems()      -----> Publisher
	 * Subscriber -----> subscribe          -----> Publisher
	 * Subscriber <----- subscription       <----- Publisher
	 * Subscriber -----> request(1)         -----> Publisher
	 * Subscriber <----- onNext(item)       <----- Publisher
	 * Subscriber -----> request(1)         -----> Publisher
	 * Subscriber <----- onNext(N-item)     <----- Publisher
	 * Subscriber -----> cancel()           -----> Publisher
	 */
	@Test
	public void test001BackPressure() {
		System.out.println("TEST test001BackPressure ******");
		Flux<Integer> flux = Flux.range(1, 10).log();
		
		StepVerifier.create(flux)
			.expectSubscription()
			.thenRequest(1)
			.expectNext(1)
			.thenRequest(1)
			.expectNext(2)
			.thenCancel()
			.verify();
	}
	
	@Test
	public void test002BackPressureWithSubscriber() {
		System.out.println("\n\nTEST test002BackPressureWithSubscriber ******");
		Flux<Integer> flux = Flux.range(1, 10).log();
		
		flux.subscribe((e) -> System.out.println("Element is " + e)
			, (e) -> System.err.println("Exception is " + e)
			, () -> System.out.println("Done") //Never executed because we cancel after getting 2 elements
			, (s -> s.request(2)));
	}
	
	@Test
	public void test003BackPressureWithSubscriberAndCancel() {
		System.out.println("\n\nTEST test003BackPressureWithSubscriberAndCancel ******");
		Flux<Integer> flux = Flux.range(1, 10).log();
		
		flux.subscribe((e) -> System.out.println("Element is " + e)
			, (e) -> System.err.println("Exception is " + e)
			, () -> System.out.println("Done")
			, (s -> s.cancel())); //Nothing will be showed because of this cancel, we're canceling the subscription
	}
	
	@Test
	public void test004BackPressureWithCustomize() {
		System.out.println("\n\nTEST test004BackPressureWithSubscriberAndCancel ******");
		Flux<Integer> flux = Flux.range(1, 10).log();
		
		flux.subscribe(new BaseSubscriber<Integer>() {
				protected void hookOnNext(Integer value) {
					request(1);
					System.out.println("Value received is " + value);
					if(value == 4)
						cancel();
				}
			});
	}
}
