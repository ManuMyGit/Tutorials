package org.mjjaenl.reactivetutorial.fluxandmonobackground;

import java.time.Duration;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FluxTimeTest {
	@Test
	public void test001InfiniteFluxWithSleep() throws InterruptedException {
		System.out.println("TEST test001InfiniteFluxWithSleep ******");
		Flux<Long> infiniteFlux = Flux.interval(Duration.ofMillis(200)).log();
		infiniteFlux.subscribe((e) -> System.out.println("Value is " + e));
		
		//Flux is being executed in another thread, asynchronously, that's why we need to use this sleep to see the results. Test case is running in the main thread, whereas Flux is executed on another thread.
		Thread.sleep(3000);
	}
	
	@Test
	public void test002InfiniteFluxWithMap() throws InterruptedException {
		System.out.println("\n\nTEST test002InfiniteFluxWithMap ******");
		Flux<Integer> infiniteFlux = Flux.interval(Duration.ofMillis(200)) //Flux<Long>
				.map(l -> new Integer(l.intValue())) //Flux<Integer>
				.take(3) //This method takes an value 
				.log();
		StepVerifier.create(infiniteFlux)
			.expectSubscription()
			.expectNext(0, 1, 2)
			.verifyComplete();
	}
	
	@Test
	public void test003InfiniteFluxWithMapAndDelay() throws InterruptedException {
		System.out.println("\n\nTEST test003InfiniteFluxWithMapAndDelay ******");
		Flux<Integer> infiniteFlux = Flux.interval(Duration.ofMillis(200)) //Flux<Long>
				.delayElements(Duration.ofSeconds(1))
				.map(l -> new Integer(l.intValue())) //Flux<Integer>
				.take(3) //This method takes an value 
				.log();
		StepVerifier.create(infiniteFlux)
			.expectSubscription()
			.expectNext(0, 1, 2)
			.verifyComplete();
	}
}
