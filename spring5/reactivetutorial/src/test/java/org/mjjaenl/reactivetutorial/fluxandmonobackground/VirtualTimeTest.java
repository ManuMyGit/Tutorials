package org.mjjaenl.reactivetutorial.fluxandmonobackground;

import java.time.Duration;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VirtualTimeTest {
	@Test
	public void test001WithoutVirtualTime() throws InterruptedException {
		System.out.println("TEST test001WithoutVirtualTime ******");
		Flux<Long> flux = Flux.interval(Duration.ofSeconds(1))
				.take(3);
		
		//For this test, we need to wait for 3 seconds.
		StepVerifier.create(flux.log())
			.expectSubscription()
			.expectNext(0L, 1L, 2L)
			.verifyComplete();
	}
	
	@Test
	public void test002WithVirtualTime() {
		System.out.println("\n\nTEST test002WithVirtualTime ******");
		VirtualTimeScheduler.getOrSet();
		Flux<Long> flux = Flux.interval(Duration.ofSeconds(1))
				.take(3);
		
		//To avoid waiting for the whole time, we can simulate the time. As you can see, this test took less than some millisecconds.
		StepVerifier.withVirtualTime(() -> flux.log())
			.expectSubscription()
			.thenAwait(Duration.ofSeconds(3))
			.expectNext(0L, 1L, 2L)
			.verifyComplete();
	}
	
	@Test
	public void test003WithVirtualTime() {
		System.out.println("\n\nTEST test003WithVirtualTime ******");
		VirtualTimeScheduler.getOrSet();
		Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
		Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));
		
		//In contrast to merge, concat waits for the arrival of the flux1 to concat it with flux2. Concat ensures the order. Flux2 will start emiting the values when flux1 finishes.
		Flux<String> mergedFlux = Flux.concat(flux1, flux2);
		
		StepVerifier.withVirtualTime(() -> mergedFlux.log())
			.expectSubscription()
			.thenAwait(Duration.ofSeconds(6))
			.expectNextCount(6)
			.verifyComplete();
	}
}
